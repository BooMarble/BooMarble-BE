package com.likelion.boomarble.domain.prediction.dto;

import com.likelion.boomarble.domain.model.ChineseType;
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
public class PredictionChineseInfoDTO {
    private String semester; // 학기
    private Country country; // 나라
    private long universityId; // 대학
    private ExType exType; // 교환유형
    private double grade; // 학점
    private ChineseType chineseType; // 유학 유형
    private String testType; // 중국어 유형
    private String level; // 중국어 급수
    private int score; // 점수
}
