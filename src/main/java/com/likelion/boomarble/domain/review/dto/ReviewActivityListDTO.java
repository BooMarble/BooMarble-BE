package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ReviewActivityListDTO {
    private List<ReviewActivityDTO> reviewActivityDTOList;

    public static ReviewActivityListDTO from(List<Review> reviews, Long currentUserId){
        List<ReviewActivityDTO> activityDTOList = reviews.stream()
                .map(review -> ReviewActivityDTO.of(review, currentUserId))
                .collect(Collectors.toList());
        return new ReviewActivityListDTO(activityDTOList);
    }
}
