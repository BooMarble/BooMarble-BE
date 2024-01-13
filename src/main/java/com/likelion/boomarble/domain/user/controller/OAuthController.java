package com.likelion.boomarble.domain.user.controller;

import com.likelion.boomarble.domain.user.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class OAuthController {

    OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService){
        this.oAuthService = oAuthService;
    }

    @GetMapping("/code/{registraionId}")
    public ResponseEntity<String> googleLogin(@RequestParam String code, @PathVariable String registraionId) throws Exception {
        String token = oAuthService.socialLogin(code, registraionId);
        return ResponseEntity.ok(token);

    }
}