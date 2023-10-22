package com.likelion.boomarble.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {

    USA("미국");
//    CAN("캐나다"),
//    GBR("영국"),
//    CZE("체코");

    private String name;

}