package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityNameDTO {
    private String name;

    public static UniversityNameDTO of(UniversityInfo universityInfo){
        return UniversityNameDTO.builder()
                .name(universityInfo.getName()).build();
    }
}
