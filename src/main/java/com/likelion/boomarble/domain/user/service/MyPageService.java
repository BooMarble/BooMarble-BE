package com.likelion.boomarble.domain.user.service;

import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.prediction.dto.PredictionListDTO;
import com.likelion.boomarble.domain.review.dto.ReviewListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;

public interface MyPageService {
    UniversityInfoListDTO getLikeUniversityList(long userId);
    CommunityListDTO getScrapCommunityList(long userId);
    ReviewListDTO getScrapReviewList(long userId);
    PredictionListDTO getApplyList(long userId);
    CommunityListDTO getCommunityPostList(long userId);
    ReviewListDTO getReviewPostList(long userId);
}