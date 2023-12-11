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
public class ReviewCostListDTO {
    private List<ReviewCostDTO> reviewCostDTOList;

    public static ReviewCostListDTO from(List<Review> reviews, Long currentUserId){
        List<ReviewCostDTO> costDTOList = reviews.stream()
                .map(review -> ReviewCostDTO.of(review, currentUserId))
                .collect(Collectors.toList());
        return new ReviewCostListDTO(costDTOList);
    }
}
