package com.likelion.boomarble.domain.universityInfo.domain;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.dto.RegisterUniversityInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class UniversityInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private ExType exType;
    private Country country;
    private String period;
    private int recruitNum;
    private float gradeQ;
    private int ibtQ;
    private int toeflQ;
    private float ieltsQ;
    private String japaneseQ;
    private String chineseQ;
    private String qualificationEtc;
    private String expCost;
    private String expCostDesc;
    private String benefit;
    private String etc;

    @Builder
    public UniversityInfo(String name, ExType exType, Country country, String period, int recruitNum, float gradeQ, int ibtQ, int toeflQ, float ieltsQ, String japaneseQ, String chineseQ, String qualificationEtc, String expCost, String expCostDesc, String benefit, String etc) {
        this.name = name;
        this.exType = exType;
        this.country = country;
        this.period = period;
        this.recruitNum = recruitNum;
        this.gradeQ = gradeQ;
        this.ibtQ = ibtQ;
        this.toeflQ = toeflQ;
        this.ieltsQ = ieltsQ;
        this.japaneseQ = japaneseQ;
        this.chineseQ = chineseQ;
        this.qualificationEtc = qualificationEtc;
        this.expCost = expCost;
        this.expCostDesc = expCostDesc;
        this.benefit = benefit;
        this.etc = etc;
    }

    @Builder
    public UniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO){
        this.name = registerUniversityInfoDTO.getUniversityName();
        this.exType = registerUniversityInfoDTO.getExType();
        this.country = registerUniversityInfoDTO.getCountry();
        this.period = registerUniversityInfoDTO.getPeriod();
        this.recruitNum = registerUniversityInfoDTO.getRecruitNum();
        this.gradeQ = registerUniversityInfoDTO.getGradeQ();
        this.ibtQ = registerUniversityInfoDTO.getIbtQ();
        this.toeflQ = registerUniversityInfoDTO.getToeflQ();
        this.ieltsQ = registerUniversityInfoDTO.getIelts();
        this.japaneseQ = registerUniversityInfoDTO.getJapaneseQ();
        this.chineseQ = registerUniversityInfoDTO.getChineseQ();
        this.qualificationEtc = registerUniversityInfoDTO.getQualificationEtc();
        this.expCost = registerUniversityInfoDTO.getExpCost();
        this.expCostDesc = registerUniversityInfoDTO.getExpCostDesc();
        this.benefit = registerUniversityInfoDTO.getBenefit();
        this.etc = registerUniversityInfoDTO.getEtc();
    }
}