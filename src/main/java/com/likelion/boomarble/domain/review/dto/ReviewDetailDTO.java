package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailDTO {

    private User writer;
    private String semester;
    private String dormitoryName;
    private String dormitoryDesc;
    private String admission;
    private String fee;
    private String preparationEtc;
    private String transportation;
    private String enrollment;
    private String program;
    private List<Subjects> subjects;
    private String activities;
    private String totalCost;
    private String airfare;
    private String insurance;
    private String costEtc;
    private String etc;
    private String acceptedGrade;
    private String message;
    private String exType;
    private String country;
    private String universityName;
    private Long universityId;

    public static ReviewDetailDTO from(Review review){
        return ReviewDetailDTO.builder()
                .writer(review.getWriter())
                .semester(review.getSemester())
                .dormitoryName(review.getDormitoryName())
                .dormitoryDesc(review.getDormitoryDesc())
                .admission(review.getAdmission())
                .fee(review.getFee())
                .preparationEtc(review.getPreparationEtc())
                .transportation(review.getTransportation())
                .enrollment(review.getEnrollment())
                .program(review.getProgram())
                .subjects(review.getSubjects())
                .activities(review.getActivities())
                .totalCost(review.getTotalCost())
                .airfare(review.getAirfare())
                .insurance(review.getInsurance())
                .costEtc(review.getCostEtc())
                .etc(review.getEtc())
                .acceptedGrade(review.getAcceptedGrade())
                .message(review.getMessage())
                .exType(review.getExType().getName())
                .country(review.getCountry().getName())
                .universityId(review.getUniversityInfo().getId())
                .universityName(review.getUniversityInfo().getName())
                .build();
    }

}
