package com.likelion.boomarble.global.service;

import com.likelion.boomarble.domain.model.dto.SearchRankResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void incrementSearchKeywordScore(String keyword) {
        String key = "searchKeywords";
        redisTemplate.opsForZSet().incrementScore(key, keyword, 1);
    }

    public List<SearchRankResponseDTO> getTopSearchKeywords(int topN) {
        String key = "searchKeywords";
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, topN - 1);
        return typedTuples.stream().map(SearchRankResponseDTO::convertToResponseRankingDto).collect(Collectors.toList());
    }
}