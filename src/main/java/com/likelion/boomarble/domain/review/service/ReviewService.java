package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import org.springframework.stereotype.Service;

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
    ReviewDetailDTO getReview(Long reviewId);
    Review createReview(Long userId, ReviewCreateDTO reviewCreateDTO);

}
