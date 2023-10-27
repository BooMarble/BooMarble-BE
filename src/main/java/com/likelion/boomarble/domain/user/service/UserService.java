package com.likelion.boomarble.domain.user.service;

import com.likelion.boomarble.domain.user.dto.UserSignUpRequestDto;

import java.util.Map;

public interface UserService {
    //회원가입
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception;
    public String login(Map<String, String> users);
}
