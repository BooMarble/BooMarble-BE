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
public class ReviewEtcListDTO {
    private List<ReviewEtcDTO> reviewEtcDTOList;

    public static ReviewEtcListDTO from(List<Review> reviews){
        List<ReviewEtcDTO> etcDTOList = reviews.stream()
                .map(ReviewEtcDTO::of)
                .collect(Collectors.toList());
        return new ReviewEtcListDTO(etcDTOList);
    }
}
