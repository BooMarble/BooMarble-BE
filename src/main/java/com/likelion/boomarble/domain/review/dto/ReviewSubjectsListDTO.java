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
public class ReviewSubjectsListDTO {
    private List<ReviewSubjectsDTO> reviewSubjectsDTOList;

    public static ReviewSubjectsListDTO from(List<Review> reviews){
        List<ReviewSubjectsDTO> subjectsDTOList = reviews.stream()
                .map(ReviewSubjectsDTO::of)
                .collect(Collectors.toList());
        return new ReviewSubjectsListDTO(subjectsDTOList);
    }
}
