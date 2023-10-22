package com.likelion.boomarble.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 아직 24-1 7+1 비영어권 모집이 발표가 안 나서 추가 예정 (10/22)
@Getter
@AllArgsConstructor
public enum Country {

    USA("미국");
//    CAN("캐나다"),
//    GBR("영국"),
//    CZE("체코");

    private String name;

}