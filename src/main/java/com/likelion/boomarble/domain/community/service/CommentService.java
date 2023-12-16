package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.dto.CommentRequestDTO;
import com.likelion.boomarble.domain.community.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, CommentRequestDTO commentRequestDTO);
    List<CommentResponseDTO> findCommentsByPostId(Long postId);

}
