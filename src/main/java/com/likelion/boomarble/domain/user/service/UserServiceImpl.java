package com.likelion.boomarble.domain.user.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.dto.UserSignUpRequestDto;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import com.likelion.boomarble.domain.user.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long signUp(UserSignUpRequestDto requestDto) throws Exception {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        User user = userRepository.save(requestDto.toEntity());

        return user.getId();
    }

    @Override
    public String login(Map<String, String> users) {
        User user = userRepository.findByEmail(users.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email입니다."));

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return jwtTokenProvider.createToken(user.getEmail(), roles);
    }

    @Override
    public int updateInfo(long userId, UserSignUpRequestDto userSignUpRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
        String nickname = userSignUpRequestDto.getNickname();
        user.setNickname(nickname);
        return 200;
    }
}
