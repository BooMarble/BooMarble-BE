package com.likelion.boomarble.domain.prediction.dto;

import com.likelion.boomarble.domain.prediction.domain.Prediction;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionViewDTO {
    private long predictionId;
    private String predictionUniversityName;
    private String predictionUniversityCountry;
    private String predictionUniversityExType;
    public static PredictionViewDTO of(Prediction prediction){
        return PredictionViewDTO.builder()
                .predictionId(prediction.getId())
                .predictionUniversityName(prediction.getUniversity().getName())
                .predictionUniversityCountry(prediction.getCountry().getName())
                .predictionUniversityExType(prediction.getExType().getName()).build();
    }
}