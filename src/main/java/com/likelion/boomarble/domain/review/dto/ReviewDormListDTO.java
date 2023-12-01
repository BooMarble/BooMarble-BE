package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDormListDTO {
    private List<ReviewDormDTO> reviewDormDTOList;

    public static ReviewDormListDTO from(List<Review> reviews){
        List<ReviewDormDTO> dormDTOList = reviews.stream()
                .map(ReviewDormDTO::of)
                .collect(Collectors.toList());
        return new ReviewDormListDTO(dormDTOList);
    }
}
