package com.mindbridge.service.post.Search;

import com.mindbridge.dto.ResponseDto.PostListResponseDto;
import com.mindbridge.dto.ResponseDto.PostSliceResponseDto;
import com.mindbridge.entity.enums.Category;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {
    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostSliceResponseDto<PostListResponseDto> searchPosts(Category category, String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Slice<PostListResponseDto> posts = postRepository.searchPostsByKeyword( category, keyword, pageRequest);

        return new PostSliceResponseDto<>(category, posts.getContent(), posts.hasNext());
    }
}
