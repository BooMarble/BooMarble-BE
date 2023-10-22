package com.likelion.boomarble.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nickname;
    private String tel;
    private String major1;
    private String major2;
    private String studentId;

    @Builder
    public User(String name, String nickname, String tel, String major1, String major2, String studentId) {
        this.name = name;
        this.nickname = nickname;
        this.tel = tel;
        this.major1 = major1;
        this.major2 = major2;
        this.studentId = studentId;
    }
}