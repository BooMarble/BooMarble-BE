package com.likelion.boomarble.domain.user.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.model.repository.LikeRepository;
import com.likelion.boomarble.domain.model.repository.ScrapRepository;
import com.likelion.boomarble.domain.prediction.domain.Prediction;
import com.likelion.boomarble.domain.prediction.dto.PredictionListDTO;
import com.likelion.boomarble.domain.prediction.repository.PredictionRepository;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.ReviewListDTO;
import com.likelion.boomarble.domain.review.repository.ReviewRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ScrapRepository scrapRepository;
    private final PredictionRepository predictionRepository;
    private final CommunityRepository communityRepository;
    private final ReviewRepository reviewRepository;
    @Override
    public UniversityInfoListDTO getLikeUniversityList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<UniversityInfo> universityInfoList = likeRepository.findUniversityInfosByUserId(userId);
        return UniversityInfoListDTO.from(universityInfoList);
    }

    @Override
    public CommunityListDTO getScrapCommunityList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<Community> communityList = scrapRepository.findScrappedCommunitiesByUserId(userId);
        return CommunityListDTO.from(communityList);
    }

    @Override
    public ReviewListDTO getScrapReviewList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<Review> reviewList = scrapRepository.findScrappedReviewsByUserId(userId);
        return ReviewListDTO.from(reviewList);
    }

    @Override
    public PredictionListDTO getApplyList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<Prediction> predictionList = predictionRepository.findAllByUser(user);
        return PredictionListDTO.from(predictionList);
    }

    @Override
    public CommunityListDTO getCommunityPostList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<Community> communityList = communityRepository.findAllByWriter(user);
        return CommunityListDTO.from(communityList);
    }

    @Override
    public ReviewListDTO getReviewPostList(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        List<Review> reviewList = reviewRepository.findAllByWriter(user);
        return ReviewListDTO.from(reviewList);
    }
}