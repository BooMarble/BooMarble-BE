package com.likelion.boomarble.domain.chat.repository;
import com.likelion.boomarble.domain.chat.domain.ChatMessage;
import com.likelion.boomarble.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByStatusAndChatRoomIdOrderByCreatedAtDesc(Status active, Long id);
    Optional<ChatMessage> findFirstByStatusAndChatRoomIdOrderByCreatedAtDesc(Status status, Long chatRoomId);
}