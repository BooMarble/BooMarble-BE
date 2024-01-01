package com.likelion.boomarble.domain.chat.repository;
import com.likelion.boomarble.domain.chat.domain.ChatRoom;
import com.likelion.boomarble.domain.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomHashCodeAndStatus(int roomHashCode, Status active);
    Optional<ChatRoom> findByIdAndStatus(Long chatRoomId, Status active);
    Optional<ChatRoom> findByIdAndStatusAndRoomHashCodeGreaterThan(Long roomId, Status active, int i);
}