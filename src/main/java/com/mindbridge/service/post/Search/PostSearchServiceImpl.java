package com.mindbridge.service.post.Search;

import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PageResponseDto;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PageResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<PostListResponseDto> posts = postRepository.searchPostsByKeyword( category, keyword, pageRequest);

        return new PageResponseDto<>(
                category,
                posts.getContent(),
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }
}
