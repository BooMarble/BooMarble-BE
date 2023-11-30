package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UniversityInfoViewDTO {
    private String universityName;
    private String universityCountry;
    private String universityType;
    private int universityReviewCnt;

    public static UniversityInfoViewDTO of(UniversityInfo universityInfo){
        return UniversityInfoViewDTO.builder()
                .universityName(universityInfo.getName())
                .universityCountry(universityInfo.getCountry().getName())
                .universityType(universityInfo.getExType().getName())
                .universityReviewCnt(universityInfo.getReviewCount()).build();
    }
}
