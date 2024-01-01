package com.likelion.boomarble.domain.model.repository;

import com.likelion.boomarble.domain.model.Like;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndUniversityInfo(User user, UniversityInfo universityInfo);
    @Query("SELECT l.universityInfo FROM Like l JOIN l.universityInfo WHERE l.user.id = :userId")
    List<UniversityInfo> findUniversityInfosByUserId(@Param("userId") long userId);
}