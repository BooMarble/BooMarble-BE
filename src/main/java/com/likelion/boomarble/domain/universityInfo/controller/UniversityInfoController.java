package com.likelion.boomarble.domain.universityInfo.controller;

import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.service.UniversityInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class UniversityInfoController {

    private final UniversityInfoService universityInfoService;

    @GetMapping("")
    public ResponseEntity getMissionList(
            Authentication authentication,
            @RequestParam(value = "country", defaultValue = "all") String country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) String type){
        UniversityInfoListDTO universityInfoListDTO = universityInfoService.getUniversityInfoList(country, university, type);
        return ResponseEntity.ok(universityInfoListDTO);
    }
}
