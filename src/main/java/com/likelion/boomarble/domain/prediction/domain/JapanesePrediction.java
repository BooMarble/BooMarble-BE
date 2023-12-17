package com.likelion.boomarble.domain.prediction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_score", columnList = "score")
})
public class JapanesePrediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String level;
    private int score;
    private boolean hasRecommendationLetter;
    private double convertedScore;

    @Builder
    public JapanesePrediction(String level, int score, boolean hasRecommendationLetter, double convertedScore){
        this.level = level;
        this.score = score;
        this.hasRecommendationLetter = hasRecommendationLetter;
        this.convertedScore = convertedScore;
    }
}
