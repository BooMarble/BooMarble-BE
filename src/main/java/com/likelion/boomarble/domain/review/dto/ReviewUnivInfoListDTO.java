package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
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
public class ReviewUnivInfoListDTO {
    private List<ReviewUnivInfoDTO> reviewUnivInfoDTOList;

    public static ReviewUnivInfoListDTO from(List<Review> reviews, long currentUserId){
        List<ReviewUnivInfoDTO> univInfoDTOList = reviews.stream()
                .map(review -> ReviewUnivInfoDTO.of(review, currentUserId))
                .collect(Collectors.toList());
        return new ReviewUnivInfoListDTO(univInfoDTOList);
    }
}
