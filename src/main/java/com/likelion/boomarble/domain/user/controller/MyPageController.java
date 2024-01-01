package com.likelion.boomarble.domain.user.controller;

import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/mypage")
@RestController
public class MyPageController {

    private final MyPageService myPageService;
    @GetMapping("/universities")
    public ResponseEntity getLikeUniversityList(Authentication authentication){
        return ResponseEntity.ok(myPageService.getLikeUniversityList(getUserPk(authentication)));
    }
    @GetMapping("/communities")
    public ResponseEntity getScrapCommunityList(Authentication authentication){
        return ResponseEntity.ok(myPageService.getScrapCommunityList(getUserPk(authentication)));
    }
    @GetMapping("/reviews")
    public ResponseEntity getScrapReviewList(Authentication authentication){
        return ResponseEntity.ok(myPageService.getScrapReviewList(getUserPk(authentication)));
    }
    @GetMapping("/applies")
    public ResponseEntity getApplyList(Authentication authentication){
        return ResponseEntity.ok(myPageService.getApplyList(getUserPk(authentication)));
    }
    @GetMapping("/posts")
    public ResponseEntity getPostList(Authentication authentication
            , @RequestParam(value = "type", required = true) String type){
        if(type.equals("community")){
            return ResponseEntity.ok(myPageService.getCommunityPostList(getUserPk(authentication)));
        }else return ResponseEntity.ok(myPageService.getReviewPostList(getUserPk(authentication)));
    }
    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}
