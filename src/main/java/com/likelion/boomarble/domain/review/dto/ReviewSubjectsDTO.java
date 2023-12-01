package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSubjectsDTO extends ReviewCommonDTO {

    private List<Subjects> subjects;

    public static ReviewSubjectsDTO of(Review review){
        return ReviewSubjectsDTO.builder()
                .subjects(review.getSubjects())
                .writer(review.getWriter())
                .build();
    }

}
