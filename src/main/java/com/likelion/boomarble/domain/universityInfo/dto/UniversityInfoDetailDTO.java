package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UniversityInfoDetailDTO {
    private String universityName;
    private String exType; // 교환유형(교환학생, 7+1)
    private String country;
    private String period; // 파견기간
    private int recruitNum; // 선발인원
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

    public static UniversityInfoDetailDTO from(UniversityInfo universityInfo) {
        return UniversityInfoDetailDTO.builder()
                .universityName(universityInfo.getName())
                .exType(universityInfo.getExType().getName())
                .country(universityInfo.getCountry().getName())
                .period(universityInfo.getPeriod())
                .recruitNum(universityInfo.getRecruitNum())
                .gradeQ(universityInfo.getGradeQ())
                .ibtQ(universityInfo.getIbtQ())
                .toeflQ(universityInfo.getToeflQ())
                .ielts(universityInfo.getIeltsQ())
                .japaneseQ(universityInfo.getJapaneseQ())
                .chineseQ(universityInfo.getChineseQ())
                .qualificationEtc(universityInfo.getQualificationEtc())
                .expCost(universityInfo.getExpCost())
                .expCostDesc(universityInfo.getExpCostDesc())
                .benefit(universityInfo.getBenefit())
                .etc(universityInfo.getEtc()).build();
    }
}