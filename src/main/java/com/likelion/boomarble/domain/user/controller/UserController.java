package com.likelion.boomarble.domain.user.controller;

import com.likelion.boomarble.domain.user.dto.UserSignUpRequestDto;
import com.likelion.boomarble.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody UserSignUpRequestDto request)
 throws Exception {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        return userService.login(user);
    }
}