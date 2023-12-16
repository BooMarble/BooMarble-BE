package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.community.dto.CommentResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}
