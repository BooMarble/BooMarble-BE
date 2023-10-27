package com.likelion.boomarble.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExType {

    exchange("교환학생"),
    seven("7+1 파견학생");

    private String name;

}
