package com.likelion.boomarble.domain.chat.service;

import com.likelion.boomarble.domain.chat.dto.ChatMessageRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface ChatMessageService {
    void sendMessage(ChatMessageRequestDTO chatMessageRequest, long userId);

    void enter(long userPk, Long roomId);
}