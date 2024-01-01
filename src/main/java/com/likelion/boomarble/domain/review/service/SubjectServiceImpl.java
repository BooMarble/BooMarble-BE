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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public List<Subjects> getSubjects(Review review) {
        List<Subjects> subjects = subjectRepository.findByReview(review);
        return subjects;
    }

    @Override
    @Transactional
    public Subjects createSubjects(Review review, SubjectDTO subjectDTO) {
        Subjects subjects = new Subjects(review, subjectDTO);
        return subjectRepository.save(subjects);
    }

    @Override
    @Transactional
    public Subjects updateSubjects(long subjectsId, SubjectDTO subjectDTO) {
        Subjects subjects = subjectRepository.findById(subjectsId)
                .orElseThrow(() -> new SubjectsNotFoundException("해당 수강과목이 존재하지 않습니다."));
        subjects.setSubjects(subjectDTO);
        return subjectRepository.save(subjects);
    }

    @Override
    @Transactional
    public void deleteSubjects(long subjectsId) {
        Subjects subjects = subjectRepository.findById(subjectsId)
                .orElseThrow(() -> new SubjectsNotFoundException("해당 수강과목이 존재하지 않습니다."));
        subjectRepository.delete(subjects);
    }
}
