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
    UniversityInfoViewDTO getUniversity(long universityInfoId);
    ReviewAccGradeListDTO getReviewAccGrade(Authentication authentication, long universityInfoId);
    ReviewActivityListDTO getReviewActivity(Authentication authentication, long universityInfoId);
    ReviewCostListDTO getReviewCost(Authentication authentication, long universityInfoId);
    ReviewDormListDTO getReviewDorm(Authentication authentication, long universityInfoId);
    ReviewEtcListDTO getReviewEtc(Authentication authentication, long universityInfoId);
    ReviewMessageListDTO getReviewMessage(Authentication authentication, long universityInfoId);
    ReviewPrepListDTO getReviewPrep(Authentication authentication, long universityInfoId);
    ReviewSubjectsListDTO getReviewSubjects(Authentication authentication, long universityInfoId);
    ReviewUnivInfoListDTO getReviewUnivInfo(Authentication authentication, long universityInfoId);
    ReviewDetailDTO getReview(long reviewId);
    Review createReview(long userId, ReviewCreateDTO reviewCreateDTO);
    Review updateReview(Authentication authentication, long reviewId, ReviewCreateDTO reviewCreateDTO);
    void deleteReview(Authentication authentication, long reviewId);
    int scrapReview(long reviewId, long userId);
    int unscrapReview(long reviewId, long userId);

}
