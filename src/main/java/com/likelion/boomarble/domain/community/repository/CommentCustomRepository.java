package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.dto.CommentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    List<CommentResponseDTO> findByBoardId(Long id);
    Optional<Comment> findCommentByIdWithParent(Long id);
}
