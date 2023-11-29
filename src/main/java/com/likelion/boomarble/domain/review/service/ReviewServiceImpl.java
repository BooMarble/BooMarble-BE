package com.likelion.boomarble.domain.review.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.review.repository.ReviewRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.universityInfo.specification.UniversityInfoSpecifications;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final UniversityInfoRepository universityInfoRepository;
    private final UserRepository userRepository;

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
    public ReviewAccGradeListDTO getReviewAccGrade(Long universityInfoId) {

        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewAccGradeListDTO.from(reviews);

    }

    @Override
    public ReviewActivityListDTO getReviewActivity(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewActivityListDTO.from(reviews);
    }

    @Override
    public ReviewCostListDTO getReviewCost(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewCostListDTO.from(reviews);
    }

    @Override
    public ReviewDormListDTO getReviewDorm(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewDormListDTO.from(reviews);
    }

    @Override
    public ReviewEtcListDTO getReviewEtc(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewEtcListDTO.from(reviews);
    }

    @Override
    public ReviewMessageListDTO getReviewMessage(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewMessageListDTO.from(reviews);
    }

    @Override
    public ReviewPrepListDTO getReviewPrep(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewPrepListDTO.from(reviews);
    }

    @Override
    public ReviewSubjectsListDTO getReviewSubjects(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewSubjectsListDTO.from(reviews);
    }

    @Override
    public ReviewUnivInfoListDTO getReviewUnivInfo(Long universityInfoId) {
        List<Review> reviews = reviewRepository.findByUniversityInfo_Id(universityInfoId);
        return ReviewUnivInfoListDTO.from(reviews);
    }

    @Override
    public Review createReview(Long userId, ReviewDetailDTO reviewDetailDTO) {
        Optional<User> user = userRepository.findById(userId);
        Review review = new Review(reviewDetailDTO, user.get());
        return reviewRepository.save(review);
    }
}
