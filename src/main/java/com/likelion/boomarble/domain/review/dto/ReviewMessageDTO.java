package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMessageDTO extends ReviewCommonDTO {

    private String message;

    public static ReviewMessageDTO of(Review review, long currentUserId){
        return ReviewMessageDTO.builder()
                .message(review.getMessage())
                .writer(review.getWriter())
                .yesWriter(review.getWriter().getId() == currentUserId)
                .reviewId(review.getId())
                .build();
    }

}
