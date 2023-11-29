package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAccGradeDTO extends ReviewCommonDTO {

    private String acceptedGrade;

    public static ReviewAccGradeDTO of(Review review){
        return ReviewAccGradeDTO.builder()
                .acceptedGrade(review.getAcceptedGrade())
                .writer(review.getWriter())
                .semester(review.getSemester())
                .exType(review.getExType().getName())
                .country(review.getCountry().getName())
                .build();
    }

}
