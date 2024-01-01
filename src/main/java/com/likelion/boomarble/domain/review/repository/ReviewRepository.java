package com.likelion.boomarble.domain.review.repository;

import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.universityInfo.id = :universityInfoId")
    List<Review> findByUniversityInfo_Id(@Param("universityInfoId") long universityInfoId);
    List<Review> findAllByWriter(User user);
}