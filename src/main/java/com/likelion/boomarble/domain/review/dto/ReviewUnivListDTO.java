package com.likelion.boomarble.domain.review.dto;

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
public class ReviewUnivListDTO {
    private List<ReviewUnivViewDTO> reviewUnivViewLists;
    public static ReviewUnivListDTO from(List<UniversityInfo> universityInfos){
        List<ReviewUnivViewDTO> viewDTOList = universityInfos.stream()
                .map(ReviewUnivViewDTO::of)
                .collect(Collectors.toList());

        return new ReviewUnivListDTO(viewDTOList);
    }
}
