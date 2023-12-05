package com.likelion.boomarble.domain.universityInfo.domain;

import com.likelion.boomarble.domain.model.ChineseType;
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
    private ChineseType chineseType;
    private String level;
    private String score;

    public ChineseQ(ChineseType chineseType, String level, String score) {
        this.chineseType = chineseType;
        this.level = level;
        this.score = score;
    }
}
