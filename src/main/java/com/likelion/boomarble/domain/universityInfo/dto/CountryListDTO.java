package com.likelion.boomarble.domain.universityInfo.dto;

import com.likelion.boomarble.domain.model.Country;
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
public class CountryListDTO {
    private List<CountryNameWithUniversitiesDTO> countries;
}
