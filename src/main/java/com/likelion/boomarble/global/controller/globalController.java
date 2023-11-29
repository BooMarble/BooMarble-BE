package com.likelion.boomarble.global.controller;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityNameListDTO;
import com.likelion.boomarble.domain.universityInfo.service.UniversityInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class globalController {

    private final UniversityInfoService universityInfoService;

    // 종속 드롭다운 리스트를 위한 컨트롤러
    @GetMapping("/universities")
    public ResponseEntity<UniversityNameListDTO> getUniversitiesByCountry(@RequestParam Country country) {
        return ResponseEntity.ok(universityInfoService.getUniversitiedByCountry(country));
    }
}
