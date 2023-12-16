package com.likelion.boomarble.domain.prediction.domain;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_university", columnList = "university")
})
public class Prediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university")
    private UniversityInfo university;
    private double grade;
    private String semester;
    private Country country;
    private ExType exType;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private EnglishPrediction englishPrediction;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private JapanesePrediction japanesePrediction;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private ChinesePrediction chinesePrediction;

    @Builder
    public Prediction(User user, UniversityInfo university, double grade, String semester, Country country, ExType exType, EnglishPrediction englishPrediction, JapanesePrediction japanesePrediction, ChinesePrediction chinesePrediction){
        this.user = user;
        this.university = university;
        this.grade = grade;
        this.semester = semester;
        this.country = country;
        this.exType = exType;
        this.englishPrediction = englishPrediction;
        this.japanesePrediction = japanesePrediction;
        this.chinesePrediction = chinesePrediction;
    }
}
