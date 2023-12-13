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
public class ReviewPrepListDTO {
    private List<ReviewPrepDTO> reviewPrepDTOList;

    public static ReviewPrepListDTO from(List<Review> reviews, long currentUserId){
        List<ReviewPrepDTO> prepDTOList = reviews.stream()
                .map(review -> ReviewPrepDTO.of(review, currentUserId))
                .collect(Collectors.toList());
        return new ReviewPrepListDTO(prepDTOList);
    }
}
