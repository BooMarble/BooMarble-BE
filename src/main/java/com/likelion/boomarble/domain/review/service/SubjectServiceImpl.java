package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Like;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.SubjectDTO;
import com.likelion.boomarble.domain.review.dto.SubjectListDTO;
import com.likelion.boomarble.domain.review.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService{

    private final SubjectRepository subjectRepository;

    @Override
    public SubjectListDTO getSubjects(Long universityInfoId) {
        List<Subjects> subjects = subjectRepository.findByUniversityInfo_Id(universityInfoId);
        return SubjectListDTO.from(subjects);
    }

    @Override
    public Subjects createSubjects(Review review, SubjectDTO subjectDTO) {
        Subjects subjects = new Subjects(review, subjectDTO);
        return subjectRepository.save(subjects);
    }
}
