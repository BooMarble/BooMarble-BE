package com.likelion.boomarble.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChineseType {

    LT("어학연수"), // Language Training
    UG("학부수업"), // UnderGraduate
    etc("구분없음");

    private String name;

}
