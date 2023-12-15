package com.likelion.boomarble.domain.community.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommentRequestDTO;
import com.likelion.boomarble.domain.community.dto.CommentResponseDTO;
import com.likelion.boomarble.domain.community.repository.CommentRepository;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(Long postId, CommentRequestDTO commentRequestDTO){

        Comment comment = new Comment(commentRequestDTO);

        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다. id: " + commentRequestDTO.getUserId()));

        Community community = communityRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 글을 찾을 수 없습니다. id: " + postId));

        comment.updateWriter(user);
        comment.updateCommunity(community);
        comment.changeIsDeleted(Boolean.FALSE); //왜지..?

        Comment parentComment;
        if (commentRequestDTO.getParentId() != null){
            parentComment = commentRepository.findById(commentRequestDTO.getParentId())
                    .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다. id: " + commentRequestDTO.getParentId()));
            comment.updateParent(parentComment);
        }

        return commentRepository.save(comment);
    }

    @Transactional
    public List<CommentResponseDTO> findCommentsByPostId(Long postId) {
        return commentRepository.findByBoardId(postId);
    }

//    @Transactional
//    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
//        List<Comment> commentsByPost = commentRepository.findAllByCommunity(postId);
//        List<CommentResponseDTO> fullComments = new ArrayList<>();
//        for (Comment comment: commentsByPost){
//            if (comment.getParent() == null) {
//                new CommentResponseDTO(comment.getContent(), comment.getWriter())
//                fullComments.add(parentComment);
//            }
//        }
//        return
//    }
}
