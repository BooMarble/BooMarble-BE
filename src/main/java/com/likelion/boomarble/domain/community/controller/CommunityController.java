package com.likelion.boomarble.domain.community.controller;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.community.service.CommunityService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("")
    public ResponseEntity postCommunity(
            Authentication authentication,
            @RequestBody CommunityCreateDTO communityCreateDTO){
        long userId = getUserPk(authentication);
        Community result = communityService.createCommunityPost(userId, communityCreateDTO);
        if (result == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity getCommunityList(
            Authentication authentication,
            @RequestParam(value = "country", defaultValue = "all") String country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "semester", required = false) String semester) {
        CommunityListDTO communityListDTO = communityService.getCommunityList(country, university, type, semester);
        return ResponseEntity.ok(communityListDTO);
    }

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }


}
