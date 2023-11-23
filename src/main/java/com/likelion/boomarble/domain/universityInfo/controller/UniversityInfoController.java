package com.likelion.boomarble.domain.universityInfo.controller;

import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoDetailDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.service.UniversityInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class UniversityInfoController {

    private final UniversityInfoService universityInfoService;

    @GetMapping("")
    public ResponseEntity getUniversityInfoList(
            Authentication authentication,
            @RequestParam(value = "country", defaultValue = "all") String country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) String type){
        UniversityInfoListDTO universityInfoListDTO = universityInfoService.getUniversityInfoList(country, university, type);
        return ResponseEntity.ok(universityInfoListDTO);
    }

    @GetMapping("/{universityId}")
    public ResponseEntity getUniversityInfoDetail(Authentication authentication, @PathVariable long universityId){
        UniversityInfoDetailDTO universityInfoDetailDTO = universityInfoService.getUniversityInfoDetail(universityId);
        return ResponseEntity.ok(universityInfoDetailDTO);
    }

    @GetMapping("/search")
    public ResponseEntity searchUniversityInfoList(
            Authentication authentication,
            @RequestParam(value = "keyword", required = false) String keyword){
        UniversityInfoListDTO universityInfoListDTO = universityInfoService.searchUniversityInfoList(keyword);
        return ResponseEntity.ok(universityInfoListDTO);
    }
}