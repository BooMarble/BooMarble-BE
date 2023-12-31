package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.universityInfo.domain.ChineseQ;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UniversityInfoDetailDTO {
    private long universityId;
    private String universityName;
    private String exType; // 교환유형(교환학생, 7+1)
    private String country;
    private String period; // 파견기간
    private String recruitNum; // 선발인원
    private float gradeQ; // 지원자격 학점
    private int ibtQ; // 지원자격 ibt
    private int toeflQ; // 지원자격 토플
    private float ielts; // 지원자격 ielts
    private String japaneseQ; // 지원자격 jlpt
    private List<ChineseQ> chineseQ; // 지원자격 hsk
    private String qualificationEtc; // 지원자격 기타
    private String expCost; // 예상 비용
    private String expCostDesc; // 예상 비용 설명
    private String benefit; // 프로그램 혜택
    private String etc; // 비고

    public static UniversityInfoDetailDTO from(UniversityInfo universityInfo) {
        return UniversityInfoDetailDTO.builder()
                .universityId(universityInfo.getId())
                .universityName(universityInfo.getName())
                .exType(universityInfo.getExType().getName())
                .country(universityInfo.getCountry().getName())
                .period(universityInfo.getPeriod())
                .recruitNum(universityInfo.getRecruitNum())
                .gradeQ(universityInfo.getGradeQ())
                .ibtQ(universityInfo.getEnglishQ() != null ? universityInfo.getEnglishQ().getIbtQ() : -1)
                .toeflQ(universityInfo.getEnglishQ() != null ? universityInfo.getEnglishQ().getToefl() : -1)
                .ielts(universityInfo.getEnglishQ() != null ? universityInfo.getEnglishQ().getIeltsQ() : -1)
                .japaneseQ(universityInfo.getJapaneseQ() != null ? universityInfo.getJapaneseQ().getJapanese() : "")
                .chineseQ(universityInfo.getChineseQList())
                .qualificationEtc(universityInfo.getQualificationEtc())
                .expCost(universityInfo.getExpCost())
                .expCostDesc(universityInfo.getExpCostDesc())
                .benefit(universityInfo.getBenefit())
                .etc(universityInfo.getEtc()).build();
    }
}