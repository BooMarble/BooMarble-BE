package com.likelion.boomarble.domain.universityInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryNameWithUniversitiesDTO {
    private String name;
    private String englishName;
    private List<UniversityNameDTO> universities;
}
