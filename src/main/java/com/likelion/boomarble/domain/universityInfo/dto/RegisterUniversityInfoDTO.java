package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUniversityInfoDTO {
    private String universityName;
    private ExType exType; // 교환유형(교환학생, 7+1)
    private Country country; // 국가
    private String period; // 파견기간
    private String recruitNum; // 선발인원
    private float gradeQ; // 지원자격 학점
    private int ibtQ; // 지원자격 ibt
    private int toeflQ; // 지원자격 토플
    private float ielts; // 지원자격 ielts
    private String japaneseQ; // 지원자격 jlpt
    private String chineseQ; // 지원자격 hsk
    private String qualificationEtc; // 지원자격 기타
    private String expCost; // 예상 비용
    private String expCostDesc; // 예상 비용 설명
    private String benefit; // 프로그램 혜택
    private String etc; // 비고
}