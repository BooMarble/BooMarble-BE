package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    UniversityInfoListDTO getUniversityList(Country country, String university, ExType type);
    ReviewAccGradeListDTO getReviewAccGrade(Long universityInfoId);
    ReviewActivityListDTO getReviewActivity(Long universityInfoId);
    ReviewCostListDTO getReviewCost(Long universityInfoId);
    ReviewDormListDTO getReviewDorm(Long universityInfoId);
    ReviewEtcListDTO getReviewEtc(Long universityInfoId);
    ReviewMessageListDTO getReviewMessage(Long universityInfoId);
    ReviewPrepListDTO getReviewPrep(Long universityInfoId);
    ReviewSubjectsListDTO getReviewSubjects(Long universityInfoId);
    ReviewUnivInfoListDTO getReviewUnivInfo(Long universityInfoId);
    Review createReview(Long userId, ReviewDetailDTO reviewDetailDTO);

}
