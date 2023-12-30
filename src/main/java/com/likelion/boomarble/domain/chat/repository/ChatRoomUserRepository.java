package com.likelion.boomarble.domain.chat.repository;
import com.likelion.boomarble.domain.chat.domain.ChatRoomUser;
import com.likelion.boomarble.domain.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    Page<ChatRoomUser> findAllByUserIdAndStatus(Long id, Status active, Pageable pageable);
}