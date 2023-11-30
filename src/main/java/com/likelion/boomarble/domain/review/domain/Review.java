package com.likelion.boomarble.domain.review.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.ReviewDetailDTO;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "writer")
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
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonManagedReference
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
    @ManyToOne
    @JoinColumn(name = "university")
    private UniversityInfo universityInfo;

//    @Builder
//    public Review(User writer, String title, String semester, String dormitoryName, String dormitoryDesc, String preparation, String universityInfo, List<Subjects> subjects, String activities, String cost, String acceptedGrade, String message, String etc, ExType exType, Country country, UniversityInfo university) {
//        this.writer = writer;
//        this.title = title;
//        this.semester = semester;
//        this.dormitoryName = dormitoryName;
//        this.dormitoryDesc = dormitoryDesc;
//        this.preparation = preparation;
//        this.universityInfo = universityInfo;
//        this.subjects = subjects;
//        this.activities = activities;
//        this.cost = cost;
//        this.acceptedGrade = acceptedGrade;
//        this.message = message;
//        this.etc = etc;
//        this.exType = exType;
//        this.country = country;
//        this.university = university;
//    }


    public Review(ReviewDetailDTO reviewDetailDTO, User user) {
        this.writer = user;
        this.semester = reviewDetailDTO.getSemester();
        this.dormitoryName = reviewDetailDTO.getDormitoryName();
        this.dormitoryDesc = reviewDetailDTO.getDormitoryDesc();
        this.admission = reviewDetailDTO.getAdmission();
        this.fee = reviewDetailDTO.getFee();
        this.preparationEtc = reviewDetailDTO.getPreparationEtc();
        this.transportation = reviewDetailDTO.getTransportation();
        this.enrollment = reviewDetailDTO.getEnrollment();
        this.program = reviewDetailDTO.getProgram();
        this.subjects = reviewDetailDTO.getSubjects();
        this.activities = reviewDetailDTO.getActivities();
        this.totalCost = reviewDetailDTO.getTotalCost();
        this.airfare = reviewDetailDTO.getAirfare();
        this.insurance = reviewDetailDTO.getInsurance();
        this.costEtc = reviewDetailDTO.getCostEtc();
        this.etc = reviewDetailDTO.getEtc();
        this.acceptedGrade = reviewDetailDTO.getAcceptedGrade();
        this.message = reviewDetailDTO.getMessage();
        this.exType = reviewDetailDTO.getExType();
        this.country = reviewDetailDTO.getCountry();
        this.universityInfo = reviewDetailDTO.getUniversityInfo();
    }
}


