package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPrepDTO extends ReviewCommonDTO {

    private String admission;
    private String fee;
    private String preparationEtc;

    public static ReviewPrepDTO of(Review review){
        return ReviewPrepDTO.builder()
                .admission(review.getAdmission())
                .fee(review.getFee())
                .preparationEtc(review.getPreparationEtc())
                .writer(review.getWriter())
                .semester(review.getSemester())
                .exType(review.getExType().getName())
                .country(review.getCountry().getName())
                .build();
    }

}
