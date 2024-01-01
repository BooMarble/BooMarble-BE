package com.likelion.boomarble.domain.model.repository;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Scrap;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndReview(User user, Review review);
    Optional<Scrap> findByUserAndCommunity(User user, Community community);
    @Query("SELECT s.review FROM Scrap s JOIN s.review WHERE s.user.id = :userId AND s.review IS NOT NULL")
    List<Review> findScrappedReviewsByUserId(@Param("userId") long userId);

    @Query("SELECT s.community FROM Scrap s JOIN s.community WHERE s.user.id = :userId AND s.community IS NOT NULL")
    List<Community> findScrappedCommunitiesByUserId(@Param("userId") long userId);
}