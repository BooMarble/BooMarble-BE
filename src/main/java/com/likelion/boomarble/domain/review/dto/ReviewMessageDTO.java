package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMessageDTO extends ReviewCommonDTO {

    private String message;

    public static ReviewMessageDTO of(Review review){
        return ReviewMessageDTO.builder()
                .message(review.getMessage())
                .writer(review.getWriter())
                .build();
    }

}
