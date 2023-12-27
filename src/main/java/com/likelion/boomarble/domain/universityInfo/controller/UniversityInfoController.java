package com.likelion.boomarble.domain.universityInfo.controller;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoDetailDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.service.UniversityInfoService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.global.service.RedisService;
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
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) ExType type){
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

    @PostMapping("/{universityId}/like")
    public ResponseEntity likeUniversityInfo(
            Authentication authentication,
            @PathVariable long universityId){
        long userId = getUserPk(authentication);
        int result = universityInfoService.likeUniversityInfo(universityId, userId);
        if(result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if(result == 409) return ResponseEntity.badRequest().body("이미 좋아요를 눌렀습니다.");
        return ResponseEntity.ok("좋아요가 정상적으로 반영이 되었습니다.");
    }

    @DeleteMapping("/{universityId}/like")
    public ResponseEntity unlikeUniversityInfo(Authentication authentication, @PathVariable long universityId){
        long userId = getUserPk(authentication);
        int result = universityInfoService.unlikeUniversityInfo(universityId, userId);
        if(result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if(result == 404) return ResponseEntity.badRequest().body("좋아요를 누른 적이 없습니다.");
        return ResponseEntity.ok("좋아요가 정상적으로 취소가 되었습니다.");
    }

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}