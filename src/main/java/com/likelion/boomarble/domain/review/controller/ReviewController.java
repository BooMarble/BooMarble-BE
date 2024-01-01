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
            @PathVariable long universityInfoId
    ){
        UniversityInfoViewDTO universityInfoViewDTO = reviewService.getUniversity(universityInfoId);
        return ResponseEntity.ok(universityInfoViewDTO);
    }

    @GetMapping("/{universityInfoId}/accGrade")
    public ResponseEntity getAccGradeList(Authentication authentication,
                                          @PathVariable long universityInfoId,
                                          @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewAccGradeListDTO reviewAccGradeListDTO = reviewService.getReviewAccGrade(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewAccGradeListDTO);
    }

    @GetMapping("/{universityInfoId}/activity")
    public ResponseEntity getActivityList(Authentication authentication,
                                          @PathVariable long universityInfoId,
                                          @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewActivityListDTO reviewActivityListDTO = reviewService.getReviewActivity(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewActivityListDTO);
    }

    @GetMapping("/{universityInfoId}/cost")
    public ResponseEntity getCostList(Authentication authentication,
                                      @PathVariable long universityInfoId,
                                      @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewCostListDTO reviewCostListDTO = reviewService.getReviewCost(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewCostListDTO);
    }

    @GetMapping("/{universityInfoId}/dorm")
    public ResponseEntity getDormList(Authentication authentication,
                                      @PathVariable long universityInfoId,
                                      @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewDormListDTO reviewDormListDTO = reviewService.getReviewDorm(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewDormListDTO);
    }

    @GetMapping("/{universityInfoId}/etc")
    public ResponseEntity getEtcList(Authentication authentication,
                                     @PathVariable long universityInfoId,
                                     @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewEtcListDTO reviewEtcListDTO = reviewService.getReviewEtc(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewEtcListDTO);
    }

    @GetMapping("/{universityInfoId}/message")
    public ResponseEntity getMessageList(Authentication authentication,
                                         @PathVariable long universityInfoId,
                                         @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewMessageListDTO reviewMessageListDTO = reviewService.getReviewMessage(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewMessageListDTO);
    }

    @GetMapping("/{universityInfoId}/preparation")
    public ResponseEntity getPrepList(Authentication authentication,
                                      @PathVariable long universityInfoId,
                                      @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewPrepListDTO reviewPrepListDTO = reviewService.getReviewPrep(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewPrepListDTO);
    }

    @GetMapping("/{universityInfoId}/subjects")
    public ResponseEntity getSubjectList(Authentication authentication,
                                         @PathVariable long universityInfoId,
                                         @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewSubjectsListDTO reviewSubjectsListDTO = reviewService.getReviewSubjects(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewSubjectsListDTO);
    }

    @GetMapping("/{universityInfoId}/univInfo")
    public ResponseEntity getUnivInfoList(Authentication authentication,
                                          @PathVariable long universityInfoId,
                                          @RequestParam(required = false, defaultValue = "modifiedAt", value = "orderBy") String orderBy){
        ReviewUnivInfoListDTO reviewUnivInfoListDTO = reviewService.getReviewUnivInfo(authentication, universityInfoId, orderBy);
        return ResponseEntity.ok(reviewUnivInfoListDTO);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity getReview(Authentication authentication,
                                    @PathVariable long reviewId){
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
            @PathVariable long reviewId,
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
        for (Subjects existingSubject : existingSubjects){
            Optional<SubjectDTO> updatedSubjectDTO = updatedSubjectDTOS.stream()
                    .filter(subjectDTO -> subjectDTO.getCode().equals(existingSubject.getCode()))
                    .findFirst();
            if (updatedSubjectDTO.isEmpty()){
                subjectService.deleteSubjects(existingSubject.getId());
            }
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReview(
            Authentication authentication,
            @PathVariable long reviewId){
        reviewService.deleteReview(authentication, reviewId);
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/review/{reviewId}/scrap")
    public ResponseEntity scrapReview(
            Authentication authentication,
            @PathVariable long reviewId
    ){
        long userId = getUserPk(authentication);
        int result = reviewService.scrapReview(reviewId, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if (result == 409) return ResponseEntity.badRequest().body("이미 스크랩되었습니다.");
        return ResponseEntity.ok("스크랩 완료되었습니다.");
    }

    @DeleteMapping("/review/{reviewId}/scrap")
    public ResponseEntity unscrapReview(
            Authentication authentication,
            @PathVariable long reviewId
    ){
        long userId = getUserPk(authentication);
        int result = reviewService.unscrapReview(reviewId, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if (result == 404) return ResponseEntity.badRequest().body("스크랩한 기록이 없습니다.");
        return ResponseEntity.ok("스크랩이 정상적으로 취소되었습니다.");
    }

    private long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }

}
