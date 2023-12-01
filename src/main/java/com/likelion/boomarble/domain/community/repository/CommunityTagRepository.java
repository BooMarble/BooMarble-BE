package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityTagRepository extends JpaRepository<CommunityTagMap, Long> {
    List<CommunityTagMap> findAllByCommunity(Community community);
}
