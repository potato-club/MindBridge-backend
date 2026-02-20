package com.mindbridge.service.post.Popular;

import com.mindbridge.dto.ResponseDto.post.PostPopularResponseDto;
import com.mindbridge.entity.PostEntity;
import com.mindbridge.mapper.PostMapper;
import com.mindbridge.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostPopularServiceImpl implements PostPopularService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public List<PostPopularResponseDto> getWeeklyTopPosts() {
        LocalDateTime oneWeekAge = LocalDateTime.now().minusDays(7);

        List<PostEntity> topPosts =
                postRepository.findTop3ByCreatedAtAfterOrderByViewCountDesc(oneWeekAge);

        return postMapper.toPopularDtoList(topPosts);
    }
}
