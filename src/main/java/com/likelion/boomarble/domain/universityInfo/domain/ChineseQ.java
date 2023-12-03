package com.likelion.boomarble.domain.universityInfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChineseQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Boolean isUG;
    private String level;
    private int score;

    @Builder
    public ChineseQ(UniversityInfo university){}

}
