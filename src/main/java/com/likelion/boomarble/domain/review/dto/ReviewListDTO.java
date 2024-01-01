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
public class ReviewListDTO {
    private List<ReviewViewDTO> reviewLists;
    public static ReviewListDTO from(List<Review> reviews){
        List<ReviewViewDTO> viewDTOList = reviews.stream()
                .map(ReviewViewDTO::of)
                .collect(Collectors.toList());

        return new ReviewListDTO(viewDTOList);
    }
}