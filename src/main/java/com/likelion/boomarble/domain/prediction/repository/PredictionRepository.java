package com.likelion.boomarble.domain.prediction.repository;

import com.likelion.boomarble.domain.model.ChineseType;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.prediction.domain.Prediction;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    @Query(value = "SELECT jp.converted_score, ROW_NUMBER() OVER (ORDER BY jp.converted_score DESC) as rankNum, p.user as userId " +
            "FROM Prediction p " +
            "LEFT JOIN japanese_prediction jp ON p.japanese_prediction_id = jp.id " +
            "WHERE p.university = :universityId AND p.ex_type = :exType",
            nativeQuery = true)
    List<Object[]> findJapaneseRankingsByUniversityId(@Param("universityId") long universityId, @Param("exType") int exType);

    @Query(value = "SELECT cp.converted_score, ROW_NUMBER() OVER (ORDER BY cp.converted_score DESC) as rankNum, p.user as userId " +
            "FROM Prediction p " +
            "LEFT JOIN chinese_prediction cp ON p.chinese_prediction_id = cp.id " +
            "WHERE p.university = :universityId AND p.ex_type = :exType AND cp.chinese_type = :type",
            nativeQuery = true)
    List<Object[]> findChineseRankingsByUniversityId(@Param("universityId") long universityId, @Param("exType") int exType, @Param("type") int chineseType);
    Prediction findByUserAndExTypeAndUniversity(User user, ExType exType, UniversityInfo universityInfo);
}