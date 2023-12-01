package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCostDTO extends ReviewCommonDTO {

    private String totalCost;
    private String airfare;
    private String insurance;
    private String costEtc;

    public static ReviewCostDTO of(Review review){
        return ReviewCostDTO.builder()
                .totalCost(review.getTotalCost())
                .airfare(review.getAirfare())
                .insurance(review.getInsurance())
                .costEtc(review.getCostEtc())
                .writer(review.getWriter())
                .build();
    }

}
