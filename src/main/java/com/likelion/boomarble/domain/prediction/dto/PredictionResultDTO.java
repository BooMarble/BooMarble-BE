package com.likelion.boomarble.domain.prediction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResultDTO {
    private int numOfApplicant; // 총 지원자 수
    private List<RankScoreDTO> rankings;
    private List<String> precautions;
}