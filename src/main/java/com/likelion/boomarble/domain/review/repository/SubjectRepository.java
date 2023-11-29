package com.likelion.boomarble.domain.review.repository;

import com.likelion.boomarble.domain.review.domain.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subjects, Long> {
    @Query("SELECT s FROM Subjects s WHERE s.review.universityInfo.id = :universityInfoId")
    List<Subjects> findByUniversityInfo_Id(@Param("universityInfoId") Long universityInfoId);
}
