package com.likelion.boomarble.domain.model.dto;

import com.likelion.boomarble.domain.universityInfo.dto.CountryNameWithUniversitiesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRankResponseDTO {
    private String rankKeyword;
    private int cnt;

    public static SearchRankResponseDTO convertToResponseRankingDto(ZSetOperations.TypedTuple typedTuple) {
        return SearchRankResponseDTO.builder()
                .rankKeyword(typedTuple.getValue().toString())
                .cnt(typedTuple.getScore().intValue()).build();
    }
}