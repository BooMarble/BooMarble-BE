package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewActivityDTO extends ReviewCommonDTO {

    private String activities;

    public static ReviewActivityDTO of(Review review, long currentUserId){
        return ReviewActivityDTO.builder()
                .activities(review.getActivities())
                .writer(review.getWriter())
                .yesWriter(review.getWriter().getId() == currentUserId)
                .build();
    }

}
