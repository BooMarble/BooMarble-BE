package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.SubjectDTO;
import com.likelion.boomarble.domain.review.dto.SubjectListDTO;
import org.springframework.stereotype.Service;

@Service
public interface SubjectService {

    public SubjectListDTO getSubjects(Long reviewId);
    public Subjects createSubjects(Review review, SubjectDTO subjectDTO);

}
