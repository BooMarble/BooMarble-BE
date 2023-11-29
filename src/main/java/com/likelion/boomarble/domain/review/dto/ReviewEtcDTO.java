package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEtcDTO extends ReviewCommonDTO {

    private String etc;

    public static ReviewEtcDTO of(Review review){
        return ReviewEtcDTO.builder()
                .etc(review.getEtc())
                .writer(review.getWriter())
                .semester(review.getSemester())
                .exType(review.getExType().getName())
                .country(review.getCountry().getName())
                .build();
    }

}
