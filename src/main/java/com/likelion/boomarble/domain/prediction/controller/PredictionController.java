package com.likelion.boomarble.domain.prediction.controller;

import com.likelion.boomarble.domain.prediction.dto.*;
import com.likelion.boomarble.domain.prediction.service.PredictionService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prediction")
public class PredictionController {

    private final PredictionService predictionService;

    // 영어권인지 비영어권이지 구별하기 위한 컨트롤러
    @PostMapping("")
    public ResponseEntity determineRegion(Authentication authentication, @RequestBody BasicInformationDTO basicInformationDTO){
        return ResponseEntity.ok().body(predictionService.determineRegionByCountry(basicInformationDTO));
    }

    @PostMapping("/japanese")
    public ResponseEntity applyJapanesePrediction(Authentication authentication, @RequestBody PredictionJapaneseInfoDTO predictionJapaneseInfoDTO){
        return ResponseEntity.ok(predictionService.applyJapanesePrediction(getUserPk(authentication), predictionJapaneseInfoDTO));
    }

    @PostMapping("/chinese")
    public ResponseEntity applyChinesePrediction(Authentication authentication, @RequestBody PredictionChineseInfoDTO predictionChineseInfoDTO){
        return ResponseEntity.ok(predictionService.applyChinesePrediction(getUserPk(authentication), predictionChineseInfoDTO));
    }

    @PostMapping("/english")
    public ResponseEntity applyEnglishPrediction(Authentication authentication, @RequestBody PredictionEnglishInfoDTO predictionEnglishInfoDTO){
        return ResponseEntity.ok(predictionService.applyEnglishPrediction(getUserPk(authentication), predictionEnglishInfoDTO));
    }

    @GetMapping("/{predictionId}")
    public ResponseEntity getPredictionDetail(Authentication authentication, @PathVariable long predictionId){
        return ResponseEntity.ok(predictionService.getPredictionDetail(getUserPk(authentication), predictionId));
    }

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}