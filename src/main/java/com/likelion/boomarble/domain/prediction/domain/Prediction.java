package com.likelion.boomarble.domain.prediction.domain;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Prediction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "university")
    private UniversityInfo university;
    private int grade;
    private int ibt;
    private int toefl;
    private int ielts;
    private String semester;

    @Builder
    public Prediction(User user, UniversityInfo university, int grade, int ibt, int toefl, int ielts, String semester) {
        this.user = user;
        this.university = university;
        this.grade = grade;
        this.ibt = ibt;
        this.toefl = toefl;
        this.ielts = ielts;
        this.semester = semester;
    }
}
