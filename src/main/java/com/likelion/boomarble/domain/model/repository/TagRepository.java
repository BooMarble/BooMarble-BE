package com.likelion.boomarble.domain.model.repository;

import com.likelion.boomarble.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tagName);
}
