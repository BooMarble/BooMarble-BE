package com.likelion.boomarble.domain.community.controller;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.*;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.community.service.CommentService;
import com.likelion.boomarble.domain.community.service.CommunityService;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommunityController  {

    private final CommunityService communityService;
    private final CommentService commentService;

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }

    @PostMapping("")
    public ResponseEntity postCommunity(
            Authentication authentication,
            @RequestBody CommunityCreateDTO communityCreateDTO){
        long userId = getUserPk(authentication);
        Community result = communityService.createCommunityPost(userId, communityCreateDTO);
        if (result == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok("커뮤니티 글이 정상적으로 등록되었습니다. id: " + result.getId());
    }

    @GetMapping("")
    public ResponseEntity getCommunityList(
            Authentication authentication,
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(value = "university", required = false) String university,
            @RequestParam(value = "type", required = false) ExType type,
            @RequestParam(value = "semester", required = false) String semester) {
        CommunityListDTO communityListDTO = communityService.getCommunityList(country, university, type, semester);
        return ResponseEntity.ok(communityListDTO);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getCommunityDetail(Authentication authentication, @PathVariable long postId){
        CommunityDetailDTO communityDetailDTO = communityService.getCommunityDetail(postId);
        return ResponseEntity.ok(communityDetailDTO);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updateCommunity(
            Authentication authentication,
            @PathVariable long postId, @RequestBody CommunityCreateDTO communityCreateDTO){
        long userId = getUserPk(authentication);
        int result = communityService.updateCommunityPost(postId, communityCreateDTO, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        return ResponseEntity.ok("게시글이 정상적으로 수정되었습니다. id: " + postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deleteCommunity(
            Authentication authentication, @PathVariable long postId){
        long userId = getUserPk(authentication);
        int result = communityService.deleteCommunityPost(postId, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if (result == 404) return ResponseEntity.badRequest().body("해당 게시글 삭제 권한이 없습니다. id: " + postId);
        return ResponseEntity.ok("게시글이 정상적으로 삭제되었습니다. id: " + postId);
    }

    @GetMapping("/search")
    public ResponseEntity searchCommunityPost(
            @RequestParam(value="keyword") String keyword){
        List<CommunitySearchDTO> searchDTOList = communityService.getCommunitySearch(keyword);
        return ResponseEntity.ok(searchDTOList);
    }

    //comment
    @PostMapping("/{postId}/comments")
    public ResponseEntity createComment(
            Authentication authentication,
            @PathVariable long postId,
            @RequestBody CommentRequestDTO commentRequestDTO){
        long userId = getUserPk(authentication);
        commentRequestDTO.setUserId(userId);
        Comment result = commentService.createComment(postId, commentRequestDTO);
        if (result == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok("댓글이 정상적으로 등록되었습니다. id: " + result.getId());
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity getCommentList(Authentication authentication, @PathVariable long postId){
        List<CommentResponseDTO> commentList = commentService.findCommentsByPostId(postId);
        return ResponseEntity.ok(commentList);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(
            Authentication authentication,
            @PathVariable long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO){
            commentService.update(commentId, commentRequestDTO);
            return ResponseEntity.ok("댓글이 수정되었습니다. id: " + commentId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(Authentication authentication, @PathVariable long commentId){
        commentService.delete(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다. id: " + commentId);
    }

    //scrap
    @PostMapping("/{postId}/scrap")
    public ResponseEntity scrapCommunity(
            Authentication authentication,
            @PathVariable long postId
    ){
        long userId = getUserPk(authentication);
        int result = communityService.scrapCommunityPost(postId, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if (result == 409) return ResponseEntity.badRequest().body("이미 스크랩되었습니다. id: " + postId);
        return ResponseEntity.ok("스크랩 완료되었습니다. id: " + postId);
    }

    @DeleteMapping("/{postId}/scrap")
    public ResponseEntity unscrapCommunity(
            Authentication authentication,
            @PathVariable long postId
    ){
        long userId = getUserPk(authentication);
        int result = communityService.unscrapCommunityPost(postId, userId);
        if (result == 400) return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        else if (result == 404) return ResponseEntity.badRequest().body("스크랩한 기록이 없습니다. id: " + postId);
        return ResponseEntity.ok("스크랩이 정상적으로 취소되었습니다. id: " + postId);
    }

}
