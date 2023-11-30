package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReviewAccGradeListDTO {
    private List<ReviewAccGradeDTO> reviewAccGradeDTOList;

    public static ReviewAccGradeListDTO from(List<Review> reviews){
        List<ReviewAccGradeDTO> accGradeDTOList = reviews.stream()
                .map(ReviewAccGradeDTO::of)
                .collect(Collectors.toList());
        return new ReviewAccGradeListDTO(accGradeDTOList);
    }
}
