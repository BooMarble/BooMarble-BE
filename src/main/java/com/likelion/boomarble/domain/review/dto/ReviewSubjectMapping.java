package com.likelion.boomarble.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSubjectMapping {
    private ReviewDetailDTO reviewDetailDTO;
    private List<SubjectDTO> subjectListDTO;
}
