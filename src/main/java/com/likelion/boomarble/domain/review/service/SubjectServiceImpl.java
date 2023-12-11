package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Like;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.SubjectDTO;
import com.likelion.boomarble.domain.review.dto.SubjectListDTO;
import com.likelion.boomarble.domain.review.exception.SubjectsNotFoundException;
import com.likelion.boomarble.domain.review.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    @Override
    public List<Subjects> getSubjects(Review review) {
        List<Subjects> subjects = subjectRepository.findByReview(review);
        return subjects;
    }

    @Override
    public Subjects createSubjects(Review review, SubjectDTO subjectDTO) {
        Subjects subjects = new Subjects(review, subjectDTO);
        return subjectRepository.save(subjects);
    }

    @Override
    public Subjects updateSubjects(Long subjectsId, SubjectDTO subjectDTO) {
        Subjects subjects = subjectRepository.findById(subjectsId)
                .orElseThrow(() -> new SubjectsNotFoundException("해당 수강과목이 존재하지 않습니다."));
        subjects.setSubjects(subjectDTO);
        return subjectRepository.save(subjects);
    }
}
