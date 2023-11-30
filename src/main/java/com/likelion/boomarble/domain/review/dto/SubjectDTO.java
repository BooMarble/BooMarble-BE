package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SubjectDTO {

    private User writer;
    private Review review;
    private String code;
    private String className;
    private int grade;
    private int satisfied;

    public static SubjectDTO of(Subjects subjects){
        return SubjectDTO.builder()
                .review(subjects.getReview())
                .code(subjects.getCode())
                .className(subjects.getClassName())
                .grade(subjects.getGrade())
                .satisfied(subjects.getSatisfied())
                .build();
    }

}
