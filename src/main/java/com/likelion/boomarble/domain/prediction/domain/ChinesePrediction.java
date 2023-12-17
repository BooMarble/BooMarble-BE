package com.likelion.boomarble.domain.prediction.domain;

import com.likelion.boomarble.domain.model.ChineseType;
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
public class ChinesePrediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private ChineseType chineseType;
    private String testType;
    private String level;
    private int score;
    private double convertedScore;

    @Builder
    public ChinesePrediction(ChineseType chineseType, String testType, String level, int score, double convertedScore){
        this.chineseType = chineseType;
        this.testType = testType;
        this.level = level;
        this.score = score;
        this.convertedScore = convertedScore;
    }
}
