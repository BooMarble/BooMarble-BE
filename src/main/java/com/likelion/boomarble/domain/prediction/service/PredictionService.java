package com.likelion.boomarble.domain.prediction.service;

import com.likelion.boomarble.domain.prediction.dto.BasicInformationDTO;
import com.likelion.boomarble.domain.prediction.dto.PredictionChineseInfoDTO;
import com.likelion.boomarble.domain.prediction.dto.PredictionJapaneseInfoDTO;
import com.likelion.boomarble.domain.prediction.dto.PredictionResultDTO;
import org.springframework.stereotype.Service;

@Service
public interface PredictionService {

    String determineRegionByCountry(BasicInformationDTO basicInformationDTO);

    PredictionResultDTO applyJapanesePrediction(long userId, PredictionJapaneseInfoDTO predictionJapaneseInfoDTO);

    PredictionResultDTO applyChinesePrediction(long userId, PredictionChineseInfoDTO predictionChineseInfoDTO);
}
