package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUnivInfoDTO extends ReviewCommonDTO{

    private String transportation;
    private String enrollment;
    private String program;

    public static ReviewUnivInfoDTO of(Review review, long currentUserId){
        return ReviewUnivInfoDTO.builder()
                .transportation(review.getTransportation())
                .enrollment(review.getEnrollment())
                .program(review.getProgram())
                .writer(review.getWriter())
                .yesWriter(review.getWriter().getId() == currentUserId)
                .build();
    }


}
