package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ReviewDormDTO extends ReviewCommonDTO{

    private String dormitoryName;
    private String dormitoryDesc;

    public static ReviewDormDTO of(Review review){
        return ReviewDormDTO.builder()
                .dormitoryName(review.getDormitoryName())
                .dormitoryDesc(review.getDormitoryDesc())
                .writer(review.getWriter())
                .build();
    }

}
