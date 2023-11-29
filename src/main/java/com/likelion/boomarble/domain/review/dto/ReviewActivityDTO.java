package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewActivityDTO extends ReviewCommonDTO {

    private String activities;

    public static ReviewActivityDTO of(Review review){
        return ReviewActivityDTO.builder()
                .activities(review.getActivities())
                .writer(review.getWriter())
                .semester(review.getSemester())
                .exType(review.getExType().getName())
                .country(review.getCountry().getName())
                .build();
    }

}
