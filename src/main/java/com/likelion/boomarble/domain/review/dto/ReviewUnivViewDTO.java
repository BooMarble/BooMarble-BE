package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUnivViewDTO {
    private String universityName;
    private String universityCountry;
    private String universityType;
    private String universitySemester;
    private Long universityReviewCnt;

    public static ReviewUnivViewDTO of(UniversityInfo universityInfo){
        return ReviewUnivViewDTO.builder()
                .universityName(universityInfo.getName())
                .universityCountry(universityInfo.getCountry().getName())
                .universityType(universityInfo.getExType().getName())
                .universitySemester(universityInfo.getPeriod())
                .universityReviewCnt((long) universityInfo.getReviewCount())
                .build();
    }


}
