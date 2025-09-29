package com.mindbridge.service;


import com.mindbridge.dto.RequestDto.PostCommentCreateRequestDto;
import com.mindbridge.dto.RequestDto.PostCommentUpdateRequestDto;
import com.mindbridge.dto.ResponseDto.PostCommentResponseDto;
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

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {
    private final PostCommentMapper postCommentMapper;
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final PostRedisService postRedisService;

    @Override
    @Transactional
    public PostCommentResponseDto createComment(Long postId, PostCommentCreateRequestDto postCommentCreateRequestDto) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));


        // 2. parentId 결정
        Long parentId = postCommentCreateRequestDto.parentId();

        if (parentId != null) {
            // 대댓글: 부모 댓글 존재 여부 확인
            boolean parentExists = postCommentRepository.existsById(parentId);
            if (!parentExists) {
                throw new PostNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
            }
        }

        // 3. 댓글 엔티티 생성
        CommentEntity comment = CommentEntity.builder()
                .userId(postCommentCreateRequestDto.userId())
                .postId(post.getId())
                .content(postCommentCreateRequestDto.content())
                .parentId(parentId)
                .isAnonymous(postCommentCreateRequestDto.isAnonymous())
                .createdAt(LocalDateTime.now())
                .build();
        comment = postCommentRepository.save(comment);

        return postCommentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public PostCommentResponseDto updateComment(Long id, PostCommentUpdateRequestDto dto) {
        CommentEntity comment = postCommentRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        comment.update(dto.content());

        return postCommentMapper.toDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        if (!postCommentRepository.existsById(id)) {
            throw new PostNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }
        postCommentRepository.deleteById(id);
    }

    @Override
    public boolean likeComment(Long commentId, Long userId) {
        return postRedisService.addLike("comment:" + commentId, String.valueOf(userId));
    }

    @Override
    public boolean unlikeComment(Long commentId, Long userId) {
        return postRedisService.removeLike("comment:" + commentId, String.valueOf(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostCommentResponseDto> getCommentsByPost(Long postId) {
        List<CommentEntity> allComments = postCommentRepository.findByPostId(postId);
        return postCommentMapper.toDtoList(allComments);
    }
}