package com.likelion.boomarble.global.controller;

import com.likelion.boomarble.domain.model.dto.SearchRankResponseDTO;
import com.likelion.boomarble.domain.universityInfo.dto.CountryListDTO;
import com.likelion.boomarble.domain.universityInfo.service.UniversityInfoService;
import com.likelion.boomarble.global.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
public class globalController {

    private final UniversityInfoService universityInfoService;
    private final RedisService redisService;

    // 종속 드롭다운 리스트를 위한 컨트롤러
    @GetMapping("/universities")
    public ResponseEntity<CountryListDTO> getUniversitiesByCountry() {
        return ResponseEntity.ok(universityInfoService.getUniversityByCountry());
    }
    @GetMapping("/dropDown/countries")
    public ResponseEntity<List<String>> searchCountries(@RequestParam String query) {
        List<String> countries = universityInfoService.searchCountries(query);
        return ResponseEntity.ok(countries);
    }
    @GetMapping("/dropDown/universities")
    public ResponseEntity<List<String>> searchUniversities(@RequestParam String query) {
        List<String> universities = universityInfoService.searchUniversities(query);
        return ResponseEntity.ok(universities);
    }
    @GetMapping("/dropDown/exTypes")
    public ResponseEntity<List<String>> searchExchangeTypes(@RequestParam String query) {
        List<String> exchangeTypes = universityInfoService.searchExchangeTypes(query);
        return ResponseEntity.ok(exchangeTypes);
    }
    @GetMapping("/hotKeyWord")
    public ResponseEntity getTopSearchKeywords() {
        List<SearchRankResponseDTO> topKeywords = redisService.getTopSearchKeywords(10);
        return ResponseEntity.ok(topKeywords);
    }
}