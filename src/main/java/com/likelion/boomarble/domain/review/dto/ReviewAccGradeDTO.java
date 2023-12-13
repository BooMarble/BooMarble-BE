package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAccGradeDTO extends ReviewCommonDTO {

    private String acceptedGrade;

    public static ReviewAccGradeDTO of(Review review, long currentUserId){
        return ReviewAccGradeDTO.builder()
                .acceptedGrade(review.getAcceptedGrade())
                .writer(review.getWriter())
                .yesWriter(review.getWriter().getId() == currentUserId)
                .build();
    }

}
