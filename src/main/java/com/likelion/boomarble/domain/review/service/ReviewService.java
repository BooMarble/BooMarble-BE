package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public interface ReviewService {

    UniversityInfoListDTO getUniversityList(Country country, String university, ExType type);
    UniversityInfoViewDTO getUniversity(long universityInfoId);
    ReviewAccGradeListDTO getReviewAccGrade(Authentication authentication, long universityInfoId, String orderBy);
    ReviewActivityListDTO getReviewActivity(Authentication authentication, long universityInfoId, String orderBy);
    ReviewCostListDTO getReviewCost(Authentication authentication, long universityInfoId, String orderBy);
    ReviewDormListDTO getReviewDorm(Authentication authentication, long universityInfoId, String orderBy);
    ReviewEtcListDTO getReviewEtc(Authentication authentication, long universityInfoId, String orderBy);
    ReviewMessageListDTO getReviewMessage(Authentication authentication, long universityInfoId, String orderBy);
    ReviewPrepListDTO getReviewPrep(Authentication authentication, long universityInfoId, String orderBy);
    ReviewSubjectsListDTO getReviewSubjects(Authentication authentication, long universityInfoId, String orderBy);
    ReviewUnivInfoListDTO getReviewUnivInfo(Authentication authentication, long universityInfoId, String orderBy);
    ReviewDetailDTO getReview(long reviewId);
    Review createReview(long userId, ReviewCreateDTO reviewCreateDTO);
    Review updateReview(Authentication authentication, long reviewId, ReviewCreateDTO reviewCreateDTO);
    void deleteReview(Authentication authentication, long reviewId);
    int scrapReview(long reviewId, long userId);
    int unscrapReview(long reviewId, long userId);

}
