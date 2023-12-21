package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {}
