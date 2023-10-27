package com.likelion.boomarble.domain.user.dto;

import com.likelion.boomarble.domain.user.domain.Role;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSignUpRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String checkedPassword;
    private Role role;

    @Builder
    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
