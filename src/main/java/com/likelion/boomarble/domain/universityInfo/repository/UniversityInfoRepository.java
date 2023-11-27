package com.likelion.boomarble.domain.universityInfo.repository;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityInfoRepository extends JpaRepository<UniversityInfo, Long>, JpaSpecificationExecutor<UniversityInfo> {
    List<UniversityInfo> findAllByNameContaining(String keyword);
    List<UniversityInfo> findAllByCountry(Country country);
}