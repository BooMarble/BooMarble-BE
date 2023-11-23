package com.likelion.boomarble.domain.universityInfo.domain;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private int gradeQ;
    private int ibtQ;
    private int toeflQ;
    private int ieltsQ;
    private String japaneseQ;
    private String chineseQ;
    private String qualificationEtc;
    private String expCost;
    private String expCostDesc;
    private String benefit;
    private String etc;

    @Builder
    public UniversityInfo(String name, ExType exType, Country country, String period, int recruitNum, int gradeQ, int ibtQ, int toeflQ, int ieltsQ, String japaneseQ, String chineseQ, String qualificationEtc, String expCost, String expCostDesc, String benefit, String etc) {
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
}