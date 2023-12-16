package com.likelion.boomarble.domain.prediction.controller;

import com.likelion.boomarble.domain.prediction.dto.*;
import com.likelion.boomarble.domain.prediction.service.PredictionService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prediction")
public class PredictionController {

    private final PredictionService predictionService;

    // 영어권인지 비영어권이지 구별하기 위한 컨트롤러
    @PostMapping("")
    public ResponseEntity determineRegion(Authentication authentication, @RequestBody BasicInformationDTO basicInformationDTO){
        String region = predictionService.determineRegionByCountry(basicInformationDTO);
        return ResponseEntity.ok().body(region);
    }

    @PostMapping("/japanese")
    public ResponseEntity applyJapanesePrediction(Authentication authentication, @RequestBody PredictionJapaneseInfoDTO predictionJapaneseInfoDTO){
        long userId = getUserPk(authentication);
        PredictionResultDTO predictionResultDTO = predictionService.applyJapanesePrediction(userId, predictionJapaneseInfoDTO);
        return ResponseEntity.ok(predictionResultDTO);
    }

    @PostMapping("/chinese")
    public ResponseEntity applyChinesePrediction(Authentication authentication, @RequestBody PredictionChineseInfoDTO predictionChineseInfoDTO){
        long userId = getUserPk(authentication);
        PredictionResultDTO predictionResultDTO = predictionService.applyChinesePrediction(userId, predictionChineseInfoDTO);
        return ResponseEntity.ok(predictionResultDTO);
    }

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}