package com.likelion.boomarble.domain.universityInfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JapaneseQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String japanese;

    public JapaneseQ(String japanese) {
        this.japanese = japanese;
    }
}
