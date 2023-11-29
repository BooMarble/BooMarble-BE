package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityTagRepository extends JpaRepository<CommunityTagMap, Long> {
}
