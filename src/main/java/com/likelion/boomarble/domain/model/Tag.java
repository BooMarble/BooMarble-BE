package com.likelion.boomarble.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Entity
@NoArgsConstructor @AllArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int cnt;

    @Builder
    public Tag(String name, int cnt){
        this.name = name;
        this.cnt = cnt;
    }

    public void plusCount(){
        this.cnt += 1;
    }

}
