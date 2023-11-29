package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private ExType exType;
    private Country country;
    private UniversityInfo universityInfo;

}
