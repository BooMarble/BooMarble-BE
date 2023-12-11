package com.likelion.boomarble.domain.review.controller;


import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.domain.Subjects;
import com.likelion.boomarble.domain.review.dto.*;
import com.likelion.boomarble.domain.review.service.ReviewService;
import com.likelion.boomarble.domain.review.service.SubjectService;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoViewDTO;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final SubjectService subjectService;

    @GetMapping("")
    public ResponseEntity getUniversityList(
            Authentication authentication,
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) ExType type){
        UniversityInfoListDTO universityInfoListDTO = reviewService.getUniversityList(country, university, type);
        return ResponseEntity.ok(universityInfoListDTO);
    }

    @GetMapping("/{universityInfoId}")
    public ResponseEntity getUniversity(
            Authentication authentication,
            @PathVariable Long universityInfoId
    ){
        UniversityInfoViewDTO universityInfoViewDTO = reviewService.getUniversity(universityInfoId);
        return ResponseEntity.ok(universityInfoViewDTO);
    }

    @GetMapping("/{universityInfoId}/accGrade")
    public ResponseEntity getAccGradeList(Authentication authentication,
                                          @PathVariable Long universityInfoId){
        ReviewAccGradeListDTO reviewAccGradeListDTO = reviewService.getReviewAccGrade(authentication, universityInfoId);
        return ResponseEntity.ok(reviewAccGradeListDTO);
    }

    @GetMapping("/{universityInfoId}/activity")
    public ResponseEntity getActivityList(Authentication authentication,
                                          @PathVariable Long universityInfoId){
        ReviewActivityListDTO reviewActivityListDTO = reviewService.getReviewActivity(authentication, universityInfoId);
        return ResponseEntity.ok(reviewActivityListDTO);
    }

    @GetMapping("/{universityInfoId}/cost")
    public ResponseEntity getCostList(Authentication authentication,
                                      @PathVariable Long universityInfoId){
        ReviewCostListDTO reviewCostListDTO = reviewService.getReviewCost(authentication, universityInfoId);
        return ResponseEntity.ok(reviewCostListDTO);
    }

    @GetMapping("/{universityInfoId}/dorm")
    public ResponseEntity getDormList(Authentication authentication,
                                      @PathVariable Long universityInfoId){
        ReviewDormListDTO reviewDormListDTO = reviewService.getReviewDorm(authentication, universityInfoId);
        return ResponseEntity.ok(reviewDormListDTO);
    }

    @GetMapping("/{universityInfoId}/etc")
    public ResponseEntity getEtcList(Authentication authentication,
                                     @PathVariable Long universityInfoId){
        ReviewEtcListDTO reviewEtcListDTO = reviewService.getReviewEtc(authentication, universityInfoId);
        return ResponseEntity.ok(reviewEtcListDTO);
    }

    @GetMapping("/{universityInfoId}/message")
    public ResponseEntity getMessageList(Authentication authentication,
                                         @PathVariable Long universityInfoId){
        ReviewMessageListDTO reviewMessageListDTO = reviewService.getReviewMessage(authentication, universityInfoId);
        return ResponseEntity.ok(reviewMessageListDTO);
    }

    @GetMapping("/{universityInfoId}/preparation")
    public ResponseEntity getPrepList(Authentication authentication,
                                      @PathVariable Long universityInfoId){
        ReviewPrepListDTO reviewPrepListDTO = reviewService.getReviewPrep(authentication, universityInfoId);
        return ResponseEntity.ok(reviewPrepListDTO);
    }

    @GetMapping("/{universityInfoId}/subjects")
    public ResponseEntity getSubjectList(Authentication authentication,
                                         @PathVariable Long universityInfoId){
        ReviewSubjectsListDTO reviewSubjectsListDTO = reviewService.getReviewSubjects(authentication, universityInfoId);
        return ResponseEntity.ok(reviewSubjectsListDTO);
    }

    @GetMapping("/{universityInfoId}/univInfo")
    public ResponseEntity getUnivInfoList(Authentication authentication,
                                          @PathVariable Long universityInfoId){
        ReviewUnivInfoListDTO reviewUnivInfoListDTO = reviewService.getReviewUnivInfo(authentication, universityInfoId);
        return ResponseEntity.ok(reviewUnivInfoListDTO);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity getReview(Authentication authentication,
                                    @PathVariable Long reviewId){
        ReviewDetailDTO reviewDetailDTO = reviewService.getReview(reviewId);
        return ResponseEntity.ok(reviewDetailDTO);
    }

    @PostMapping("")
    public ResponseEntity postReview(
            Authentication authentication,
            @RequestBody ReviewSubjectMapping reviewSubjectMapping
            ){
        long userId = getUserPk(authentication);
        Review result = reviewService.createReview(userId, reviewSubjectMapping.getReviewCreateDTO());
        List<SubjectDTO> subjectDTOS = reviewSubjectMapping.getSubjectListDTO();
        for (SubjectDTO subjectDTO : subjectDTOS){
            subjectService.createSubjects(result, subjectDTO);
        }

        if (result==null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(result);
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity updateReview(
            Authentication authentication,
            @PathVariable Long reviewId,
            @RequestBody ReviewSubjectMapping reviewSubjectMapping
    ){
        Review result = reviewService.updateReview(authentication, reviewId, reviewSubjectMapping.getReviewCreateDTO());
        List<Subjects> existingSubjects = subjectService.getSubjects(result);
        List<SubjectDTO> updatedSubjectDTOS = reviewSubjectMapping.getSubjectListDTO();
        for (SubjectDTO updatedSubjectDTO : updatedSubjectDTOS){
            Optional<Subjects> existingSubject = existingSubjects.stream()
                    .filter(subjects -> subjects.getCode().equals(updatedSubjectDTO.getCode()))
                    .findFirst();
            if (existingSubject.isPresent()){
                subjectService.updateSubjects(existingSubject.get().getId(), updatedSubjectDTO);
            } else {
                subjectService.createSubjects(result, updatedSubjectDTO);
            }
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReview(
            Authentication authentication,
            @PathVariable Long reviewId){
        reviewService.deleteReview(authentication, reviewId);
        return ResponseEntity.ok("삭제 완료");
    }

    private long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }

}
