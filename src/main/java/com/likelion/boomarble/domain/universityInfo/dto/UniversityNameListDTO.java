package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityNameListDTO {
    private List<UniversityNameDTO> universities;

    public static UniversityNameListDTO from(List<UniversityInfo> universities) {
        List<UniversityNameDTO> nameDTOList = universities.stream()
                .map(UniversityNameDTO::of)
                .collect(Collectors.toList());

        return new UniversityNameListDTO(nameDTOList);
    }
}
