package com.likelion.boomarble.domain.prediction.dto;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionJapaneseInfoDTO {
    private String semester;
    private Country country;
    private long universityId;
    private ExType exType;
    private double grade;
    private String level;
    private int score;
    private boolean hasRecommendationLetter;
}