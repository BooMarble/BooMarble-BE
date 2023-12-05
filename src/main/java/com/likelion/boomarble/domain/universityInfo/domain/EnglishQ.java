package com.likelion.boomarble.domain.universityInfo.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnglishQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "INT DEFAULT -1", nullable = true)
    private int ibtQ;
    @Column(columnDefinition = "INT DEFAULT -1", nullable = true)
    private int toefl;
    @Column(columnDefinition = "FLOAT DEFAULT -1", nullable = true)
    private float ieltsQ;

    public EnglishQ(int ibtQ, int toefl, float ieltsQ) {
        this.ibtQ = ibtQ;
        this.toefl = toefl;
        this.ieltsQ = ieltsQ;
    }
}
