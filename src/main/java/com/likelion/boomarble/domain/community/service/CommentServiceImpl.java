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

import java.util.List;

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

    @Override
    @Transactional
    public void update(long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다. id: " + commentId));
        comment.updateContent(commentRequestDTO.getContent());
    }

    @Override
    @Transactional
    public void delete(long commentId) {
        Comment comment = commentRepository.findCommentByBIdWithParent(commentId)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다. id: " + commentId));
        if(comment.getChildren().size() != 0){  //자식이 있으면 상태만 변경
            comment.changeIsDeleted(true);
        } else {                                //삭제 가능한 조상 댓글을 찾아서 삭제
            commentRepository.delete(getDeletableAncestorComment(comment));
        }
    }

    @Override
    @Transactional
    public Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();   //현재 댓글의 부모를 구함
        if (parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted())
            //부모가 있고, 부모의 자식이 1개(지금 삭제하는 댓글)이고, 부모의 삭제 상태가 TRUE인 댓글이라면 재귀
            return getDeletableAncestorComment(parent);
        return comment;
    }
}
