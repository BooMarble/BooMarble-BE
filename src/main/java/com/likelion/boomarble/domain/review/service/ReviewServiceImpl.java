package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Scrap;
import com.likelion.boomarble.domain.model.repository.ScrapRepository;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.review.exception.ReviewNotFoundException;
import com.likelion.boomarble.domain.review.repository.ReviewRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import com.likelion.boomarble.domain.universityInfo.exception.UniversityInfoNotFoundException;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.universityInfo.specification.UniversityInfoSpecifications;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import com.likelion.boomarble.global.error.NoPermissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UniversityInfoRepository universityInfoRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    @Override
    @Transactional
    public UniversityInfoListDTO getUniversityList(Country country, String university, ExType type) {
        Specification<UniversityInfo> spec = Specification.where(UniversityInfoSpecifications.hasCountry(country))
                .and(UniversityInfoSpecifications.hasUniversity(university))
                .and(UniversityInfoSpecifications.hasType(type));

        List<UniversityInfo> results = universityInfoRepository.findAll(spec);
        return UniversityInfoListDTO.from(results);
    }

    @Override
    @Transactional
    public UniversityInfoViewDTO getUniversity(long universityInfoId) {
        UniversityInfo universityInfo = universityInfoRepository.findById(universityInfoId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학교가 존재하지 않습니다."));
        return UniversityInfoViewDTO.of(universityInfo);
    }

    @Override
    @Transactional
    public ReviewAccGradeListDTO getReviewAccGrade(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewAccGradeListDTO.from(reviews, currentUserId);

    }

    @Override
    @Transactional
    public ReviewActivityListDTO getReviewActivity(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewActivityListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewCostListDTO getReviewCost(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewCostListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewDormListDTO getReviewDorm(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewDormListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewEtcListDTO getReviewEtc(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewEtcListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewMessageListDTO getReviewMessage(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewMessageListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewPrepListDTO getReviewPrep(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewPrepListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewSubjectsListDTO getReviewSubjects(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("modifiedAt")) {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        return ReviewSubjectsListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewUnivInfoListDTO getReviewUnivInfo(Authentication authentication, long universityInfoId, String orderBy) {
        long currentUserId = getUserPk(authentication);
        List<Review> reviews;
        if (orderBy.equals("scrap")) {
            reviews = reviewRepository.findByUniversityInfo_Id_OrderByScrapCount(universityInfoId);
        }
        else {
            reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        }
        return ReviewUnivInfoListDTO.from(reviews, currentUserId);
    }

    @Override
    @Transactional
    public ReviewDetailDTO getReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다."));
        return ReviewDetailDTO.from(review);
    }

    @Override
    @Transactional
    public Review createReview(long userId, ReviewCreateDTO reviewCreateDTO) {
        Optional<User> user = userRepository.findById(userId);
        Review review = new Review(reviewCreateDTO, user.get());
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review updateReview(Authentication authentication, long reviewId, ReviewCreateDTO reviewCreateDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다."));
        if (review.getWriter().getId() == getUserPk(authentication)){
            review.setReview(reviewCreateDTO);
            return reviewRepository.save(review);
        } else {
            throw new NoPermissionException("권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteReview(Authentication authentication, long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다."));
        if (review.getWriter().getId() == getUserPk(authentication)){
            reviewRepository.delete(review);
        } else {
            throw new NoPermissionException("권한이 없습니다.");
        }
    }

    @Override
    @Transactional
    public int scrapReview(long reviewId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다."));
        if (scrapRepository.findByUserAndReview(user, review).isPresent()) return 409;
        if (scrapRepository.save(new Scrap(user, review, null, 1)) == null) return 400;
        return 200;
    }

    @Override
    @Transactional
    public int unscrapReview(long reviewId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰가 존재하지 않습니다."));
        Optional<Scrap> scrap = scrapRepository.findByUserAndReview(user, review);
        if (scrap.isPresent()){
            scrapRepository.delete(scrap.get());
            if (scrapRepository.findByUserAndReview(user, review).isEmpty()) return 200;
            else return 400;
        } else return 404;
    }


    private long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}
