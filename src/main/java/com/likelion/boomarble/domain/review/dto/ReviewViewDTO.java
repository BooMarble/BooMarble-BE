package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewViewDTO {
    private long reviewId;
    private long userId;
    private String userNickName;
    private String universityName;
    private String universityCountry;
    private String universityType;
    private String universitySemester;

    public static ReviewViewDTO of(Review review){
        return ReviewViewDTO.builder()
                .reviewId(review.getId())
                .userId(review.getWriter().getId())
                .userNickName(review.getWriter().getNickname())
                .universityName(review.getUniversityInfo().getName())
                .universityCountry(review.getUniversityInfo().getCountry().getName())
                .universityType(review.getUniversityInfo().getExType().getName())
                .universitySemester(review.getSemester())
                .build();
    }
}