package com.likelion.boomarble.domain.community.repository;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, JpaSpecificationExecutor<Community> {
    Optional<Community> findByIdAndWriter(Long postId, User user);
}
