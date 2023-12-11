package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    UniversityInfoListDTO getUniversityList(Country country, String university, ExType type);
    UniversityInfoViewDTO getUniversity(Long universityInfoId);
    ReviewAccGradeListDTO getReviewAccGrade(Authentication authentication, Long universityInfoId);
    ReviewActivityListDTO getReviewActivity(Authentication authentication, Long universityInfoId);
    ReviewCostListDTO getReviewCost(Authentication authentication, Long universityInfoId);
    ReviewDormListDTO getReviewDorm(Authentication authentication, Long universityInfoId);
    ReviewEtcListDTO getReviewEtc(Authentication authentication, Long universityInfoId);
    ReviewMessageListDTO getReviewMessage(Authentication authentication, Long universityInfoId);
    ReviewPrepListDTO getReviewPrep(Authentication authentication, Long universityInfoId);
    ReviewSubjectsListDTO getReviewSubjects(Authentication authentication, Long universityInfoId);
    ReviewUnivInfoListDTO getReviewUnivInfo(Authentication authentication, Long universityInfoId);
    ReviewDetailDTO getReview(Long reviewId);
    Review createReview(Long userId, ReviewCreateDTO reviewCreateDTO);
    Review updateReview(Authentication authentication, Long reviewId, ReviewCreateDTO reviewCreateDTO);
    void deleteReview(Authentication authentication, Long reviewId);

}
