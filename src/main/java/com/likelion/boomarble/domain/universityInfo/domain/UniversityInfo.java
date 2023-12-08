package com.likelion.boomarble.domain.universityInfo.domain;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.universityInfo.dto.RegisterUniversityInfoDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UniversityInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private ExType exType;
    private Country country;
    private String semester;
    private String period;
    private String recruitNum;
    private float gradeQ;
    @Column(length = 2048)
    private String qualificationEtc;
    private String expCost;
    @Column(length = 2048)
    private String expCostDesc;
    private String benefit;
    @Column(length = 2048)
    private String etc;
    @OneToMany(mappedBy = "universityInfo") // Review 엔티티의 university 필드와 매핑
    private List<Review> reviews;
    @OneToOne(cascade = CascadeType.PERSIST)
    @Nullable
    private EnglishQ englishQ;
    @OneToOne(cascade = CascadeType.PERSIST)
    @Nullable
    private JapaneseQ japaneseQ;
    @OneToMany(cascade = CascadeType.PERSIST) // UniversityInfo 저장하면서 얘네도 자동으로 저장
    @Nullable
    private List<ChineseQ> chineseQList;

    // 대학 정보에 속하는 리뷰 개수를 반환하는 메서드
    public int getReviewCount() {
        return reviews != null ? reviews.size() : 0;
    }

//    @Builder
//    public UniversityInfo(String name, ExType exType, Country country, String period, String recruitNum, float gradeQ, int ibtQ, int toeflQ, float ieltsQ, String japaneseQ, String chineseQ, String qualificationEtc, String expCost, String expCostDesc, String benefit, String etc) {
//        this.name = name;
//        this.exType = exType;
//        this.country = country;
//        this.period = period;
//        this.recruitNum = recruitNum;
//        this.gradeQ = gradeQ;
//        this.qualificationEtc = qualificationEtc;
//        this.expCost = expCost;
//        this.expCostDesc = expCostDesc;
//        this.benefit = benefit;
//        this.etc = etc;
//    }

//    @Builder
//    public UniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO){
//        this.name = registerUniversityInfoDTO.getUniversityName();
//        this.exType = registerUniversityInfoDTO.getExType();
//        this.country = registerUniversityInfoDTO.getCountry();
//        this.period = registerUniversityInfoDTO.getPeriod();
//        this.recruitNum = registerUniversityInfoDTO.getRecruitNum();
//        this.gradeQ = registerUniversityInfoDTO.getGradeQ();
//        this.ibtQ = registerUniversityInfoDTO.getIbtQ();
//        this.toeflQ = registerUniversityInfoDTO.getToeflQ();
//        this.ieltsQ = registerUniversityInfoDTO.getIelts();
//        this.japaneseQ = registerUniversityInfoDTO.getJapaneseQ();
//        this.chineseQ = registerUniversityInfoDTO.getChineseQ();
//        this.qualificationEtc = registerUniversityInfoDTO.getQualificationEtc();
//        this.expCost = registerUniversityInfoDTO.getExpCost();
//        this.expCostDesc = registerUniversityInfoDTO.getExpCostDesc();
//        this.benefit = registerUniversityInfoDTO.getBenefit();
//        this.etc = registerUniversityInfoDTO.getEtc();
//    }
}