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
public class PredictionEnglishInfoDTO {
    private String semester;
    private Country country;
    private int universityId;
    private ExType exType;
    private double grade;
    private String testType;
    private int score;
}