package com.likelion.boomarble.domain.model.repository;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Scrap;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndReview(User user, Review review);
    Optional<Scrap> findByUserAndCommunity(User user, Community community);
}
