package com.likelion.boomarble.domain.review.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.dto.ReviewCreateDTO;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@AllArgsConstructor @NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;
    private String semester;
    private String dormitoryName;
    private String dormitoryDesc;
    @Lob
    private String admission;
    @Lob
    private String fee;
    @Lob
    private String preparationEtc;
    @Lob
    private String transportation;
    @Lob
    private String enrollment;
    @Lob
    private String program;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Subjects> subjects;
    @Lob
    private String activities;
    private String totalCost;
    private String airfare;
    private String insurance;
    @Lob
    private String costEtc;
    @Lob
    private String etc;
    @Lob
    private String acceptedGrade;
    @Lob
    private String message;
    //    private ExType exType;
//    private Country country;
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


    public Review(ReviewCreateDTO reviewCreateDTO, User user) {
        this.writer = user;
        this.semester = reviewCreateDTO.getSemester();
        this.dormitoryName = reviewCreateDTO.getDormitoryName();
        this.dormitoryDesc = reviewCreateDTO.getDormitoryDesc();
        this.admission = reviewCreateDTO.getAdmission();
        this.fee = reviewCreateDTO.getFee();
        this.preparationEtc = reviewCreateDTO.getPreparationEtc();
        this.transportation = reviewCreateDTO.getTransportation();
        this.enrollment = reviewCreateDTO.getEnrollment();
        this.program = reviewCreateDTO.getProgram();
        this.subjects = reviewCreateDTO.getSubjects();
        this.activities = reviewCreateDTO.getActivities();
        this.totalCost = reviewCreateDTO.getTotalCost();
        this.airfare = reviewCreateDTO.getAirfare();
        this.insurance = reviewCreateDTO.getInsurance();
        this.costEtc = reviewCreateDTO.getCostEtc();
        this.etc = reviewCreateDTO.getEtc();
        this.acceptedGrade = reviewCreateDTO.getAcceptedGrade();
        this.message = reviewCreateDTO.getMessage();
//        this.exType = ExType.valueOf(reviewCreateDTO.getExType());
//        this.country = Country.valueOf(reviewCreateDTO.getCountry());
        this.universityInfo = reviewCreateDTO.getUniversityInfo();
    }

    public void setReview(ReviewCreateDTO reviewCreateDTO) {
        this.semester = reviewCreateDTO.getSemester();
        this.dormitoryName = reviewCreateDTO.getDormitoryName();
        this.dormitoryDesc = reviewCreateDTO.getDormitoryDesc();
        this.admission = reviewCreateDTO.getAdmission();
        this.fee = reviewCreateDTO.getFee();
        this.preparationEtc = reviewCreateDTO.getPreparationEtc();
        this.transportation = reviewCreateDTO.getTransportation();
        this.enrollment = reviewCreateDTO.getEnrollment();
        this.program = reviewCreateDTO.getProgram();
        this.subjects = reviewCreateDTO.getSubjects();
        this.activities = reviewCreateDTO.getActivities();
        this.totalCost = reviewCreateDTO.getTotalCost();
        this.airfare = reviewCreateDTO.getAirfare();
        this.insurance = reviewCreateDTO.getInsurance();
        this.costEtc = reviewCreateDTO.getCostEtc();
        this.etc = reviewCreateDTO.getEtc();
        this.acceptedGrade = reviewCreateDTO.getAcceptedGrade();
        this.message = reviewCreateDTO.getMessage();
        this.universityInfo = reviewCreateDTO.getUniversityInfo();
    }
}


