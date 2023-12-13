package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEtcDTO extends ReviewCommonDTO {

    private String etc;

    public static ReviewEtcDTO of(Review review, long currentUserId){
        return ReviewEtcDTO.builder()
                .etc(review.getEtc())
                .writer(review.getWriter())
                .yesWriter(review.getWriter().getId() == currentUserId)
                .build();
    }

}
