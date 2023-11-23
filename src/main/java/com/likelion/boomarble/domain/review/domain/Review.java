package com.likelion.boomarble.domain.review.domain;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Subjects;
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
    private String title;
    private String semester;
    private String dormitoryName;
    private String dormitoryDesc;
    private String preparation;
    private String universityInfo;
    @OneToMany
    private List<Subjects> subjects;
    private String activities;
    private String cost;
    private String acceptedGrade;
    private String message;
    private String etc;
    private ExType exType;
    private Country country;
    @ManyToOne
    @JoinColumn(name = "university")
    private UniversityInfo university;

    @Builder
    public Review(User writer, String title, String semester, String dormitoryName, String dormitoryDesc, String preparation, String universityInfo, List<Subjects> subjects, String activities, String cost, String acceptedGrade, String message, String etc, ExType exType, Country country, UniversityInfo university) {
        this.writer = writer;
        this.title = title;
        this.semester = semester;
        this.dormitoryName = dormitoryName;
        this.dormitoryDesc = dormitoryDesc;
        this.preparation = preparation;
        this.universityInfo = universityInfo;
        this.subjects = subjects;
        this.activities = activities;
        this.cost = cost;
        this.acceptedGrade = acceptedGrade;
        this.message = message;
        this.etc = etc;
        this.exType = exType;
        this.country = country;
        this.university = university;
    }
}