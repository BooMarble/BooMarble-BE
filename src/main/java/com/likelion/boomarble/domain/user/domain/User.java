package com.likelion.boomarble.domain.user.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nickname;
    private String email;    //jwt에 사용
    private String password; //jwt에 사용
    private String tel;
    private String major1;
    private String major2;
    private String studentId;
    private int predictionCnt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String name, String nickname, String email, String password, String tel, String major1, String major2, String studentId, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.major1 = major1;
        this.major2 = major2;
        this.studentId = studentId;
        this.role = Role.USER;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void applyPrediction(){ this.predictionCnt-=1;}

    @PrePersist
    public void setPredictionCnt() {
        this.predictionCnt = 3;
    }
}