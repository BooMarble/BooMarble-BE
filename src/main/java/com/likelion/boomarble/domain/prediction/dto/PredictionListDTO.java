package com.likelion.boomarble.domain.prediction.dto;

import com.likelion.boomarble.domain.prediction.domain.Prediction;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PredictionListDTO {
    private List<PredictionViewDTO> prediction;
    public static PredictionListDTO from(List<Prediction> predictions) {
        List<PredictionViewDTO> viewDTOList = predictions.stream()
                .map(PredictionViewDTO::of)
                .collect(Collectors.toList());

        return new PredictionListDTO(viewDTOList);
    }
}