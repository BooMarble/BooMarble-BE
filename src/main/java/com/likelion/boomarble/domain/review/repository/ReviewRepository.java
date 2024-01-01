package com.likelion.boomarble.domain.review.repository;

import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.universityInfo.id = :universityInfoId ORDER BY r.id DESC")
    List<Review> findByUniversityInfo_Id(@Param("universityInfoId") long universityInfoId);
<<<<<<< HEAD


}
=======
    @Query("SELECT r, COUNT(s) AS scrapCount " +
            "FROM Review r " +
            "LEFT JOIN Scrap s " +
            "ON r.id = s.review.id " +
            "WHERE r.universityInfo.id = :universityInfoId " +
            "GROUP BY r " +
            "ORDER BY scrapCount DESC")
    List<Review> findByUniversityInfo_Id_OrderByScrapCount(@Param("universityInfoId") long universityInfoId);
    List<Review> findAllByWriter(User user);
}
>>>>>>> f891b66 (feat: 리뷰 최신순/스크랩순 정렬 구현)
