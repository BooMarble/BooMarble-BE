package com.likelion.boomarble.domain.prediction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankScoreDTO {
    private double score;
    private long rankNum;
    private long userId;
    private boolean isUser;
}