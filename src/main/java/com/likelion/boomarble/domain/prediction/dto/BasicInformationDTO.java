package com.likelion.boomarble.domain.prediction.dto;

import com.likelion.boomarble.domain.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicInformationDTO {
    private Country country;
}