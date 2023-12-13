package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.SubjectDTO;
import com.likelion.boomarble.domain.review.dto.SubjectListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubjectService {

    public List<Subjects> getSubjects(Review review);
    public Subjects createSubjects(Review review, SubjectDTO subjectDTO);
    public Subjects updateSubjects(long subjectsId, SubjectDTO subjectDTO);
    public void deleteSubjects(long subjectsId);

}
