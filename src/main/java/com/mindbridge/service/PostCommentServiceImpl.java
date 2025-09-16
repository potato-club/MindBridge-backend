package com.mindbridge.service;

import com.mindbridge.dto.RequestDTO.PostCommentCreateRequestDTO;
import com.mindbridge.dto.ResponseDTO.PostCommentResponseDTO;
import com.mindbridge.dto.RequestDTO.PostCommentUpdateRequestDTO;
import com.mindbridge.entity.CommentEntity;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.error.ErrorCode;
import com.mindbridge.error.customExceptions.PostNotFoundException;
import com.mindbridge.mapper.PostCommentMapper;
import com.mindbridge.repository.PostCommentRepository;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService{
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostCommentResponseDTO createComment(PostCommentCreateRequestDTO commentCreateRequestDTO) {
        PostEntity postEntity = postRepository.findById(commentCreateRequestDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        CommentEntity parent = null;
        if (commentCreateRequestDTO.getParentId() != null) {
            parent = postCommentRepository.findById(commentCreateRequestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));
        }

        CommentEntity commentEntity = CommentEntity.builder()
                .userId(commentCreateRequestDTO.getUserId())
                .postId(postEntity)
                .parent(parent)
                .content(commentCreateRequestDTO.getContent())
                .isAnonymous(commentCreateRequestDTO.getIsAnonymous())
                .build();

        CommentEntity saved = postCommentRepository.save(commentEntity);
        return PostCommentMapper.commentToDTO(saved);
    }

    @Override
    @Transactional
    public List<PostCommentResponseDTO> getCommentsByPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        return postCommentRepository.findByPostIdAndParentIsNull(postEntity)
                .stream()
                .map(PostCommentMapper::commentToDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<PostCommentResponseDTO> getCommentsWithRepliesByPost(Long postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));

        List<CommentEntity> topLevelComments = postCommentRepository.findByPostIdAndParentIsNull(postEntity);

        return topLevelComments.stream()
                .map(this::convertToDTOWithReplies)
                .toList();
    }
    private PostCommentResponseDTO convertToDTOWithReplies(CommentEntity commentEntity) {
        PostCommentResponseDTO postCommentResponseDTO = PostCommentMapper.commentToDTO(commentEntity);

        List<PostCommentResponseDTO> replyDTOs =
                commentEntity.getReplies()
                .stream()
                .map(this::convertToDTOWithReplies)
                .toList();

        postCommentResponseDTO.setReplies(replyDTOs);
        return postCommentResponseDTO;
    }

    @Override
    @Transactional
    public PostCommentResponseDTO updateComment(Long id, PostCommentUpdateRequestDTO commentUpdateRequestDTO) {
        CommentEntity commentEntity = postCommentRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        commentEntity.update(commentUpdateRequestDTO.getContent());
        return PostCommentMapper.commentToDTO(commentEntity);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        CommentEntity commentEntity = postCommentRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!commentEntity.getReplies().isEmpty()) {
            postCommentRepository.updateParentToNullByParentId(id);
        }
        postCommentRepository.deleteById(id);
    }
}
