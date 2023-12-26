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
    private long id;
    private String name;
    private String exType;
    private String englishExType;

    public static UniversityNameDTO of(UniversityInfo universityInfo){
        return UniversityNameDTO.builder()
                .id(universityInfo.getId())
                .name(universityInfo.getName())
                .exType(universityInfo.getExType().getName())
                .englishExType(universityInfo.getExType().name()).build();
    }
}
