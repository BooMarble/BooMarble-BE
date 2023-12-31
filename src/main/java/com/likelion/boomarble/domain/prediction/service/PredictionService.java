package com.likelion.boomarble.domain.prediction.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.prediction.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface PredictionService {

    String determineRegionByCountry(Country country);

    PredictionResultDTO applyJapanesePrediction(long userId, PredictionJapaneseInfoDTO predictionJapaneseInfoDTO);

    PredictionResultDTO applyChinesePrediction(long userId, PredictionChineseInfoDTO predictionChineseInfoDTO);

    PredictionResultDTO applyEnglishPrediction(long userId, PredictionEnglishInfoDTO predictionEnglishInfoDTO);

    PredictionResultDTO getPredictionDetail(long userPk, long predictionId);
}
