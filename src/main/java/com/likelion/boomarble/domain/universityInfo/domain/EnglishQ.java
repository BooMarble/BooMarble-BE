package com.likelion.boomarble.domain.universityInfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnglishQ {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int ibtQ;
    private int toefl;
    private float ieltsQ;


}
