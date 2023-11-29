package com.likelion.boomarble.domain.review.domain;

import com.likelion.boomarble.domain.review.dto.SubjectDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Subjects {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "review")
    private Review review;
    private String code;
    private String className;
    private int grade;
    private int satisfied;

//    @Builder
//    public Subjects(Review review, String code, String className, int grade, int satisfied) {
//        this.review = review;
//        this.code = code;
//        this.className = className;
//        this.grade = grade;
//        this.satisfied = satisfied;
//    }

    @Builder
    public Subjects(Review review, SubjectDTO subjectDTO){
        this.className = subjectDTO.getClassName();
        this.code = subjectDTO.getCode();
        this.grade = subjectDTO.getGrade();
        this.satisfied = subjectDTO.getSatisfied();
        this.review = review;
    }
}