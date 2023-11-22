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
public class UniversityInfoListDTO {
    private List<UniversityInfoViewDTO> universityInfoLists;
    public static UniversityInfoListDTO from(List<UniversityInfo> universityInfos) {
        List<UniversityInfoViewDTO> viewDTOList = universityInfos.stream()
                .map(UniversityInfoViewDTO::of)
                .collect(Collectors.toList());

        return new UniversityInfoListDTO(viewDTOList);
    }
}
