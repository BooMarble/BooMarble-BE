package com.likelion.boomarble.domain.prediction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class EnglishPrediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String testType;
    private int score;
    private double convertedScore;

    @Builder
    public EnglishPrediction(String testType, int score, double convertedScore){
        this.testType = testType;
        this.score = score;
        this.convertedScore = convertedScore;
    }
}
