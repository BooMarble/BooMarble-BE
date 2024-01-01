package com.likelion.boomarble.domain.chat.service;

import com.likelion.boomarble.domain.chat.dto.CreateChatRoomDTO;
import com.likelion.boomarble.domain.chat.dto.ChatRoomListDTO;
import com.likelion.boomarble.domain.chat.dto.GetChatMessageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatRoomService {
    List<ChatRoomListDTO> getChatRoomList(long userPk, Pageable pageable);

    Long createChatRoom(long userId, CreateChatRoomDTO createChatRoomDTO);

    List<GetChatMessageResponseDTO> getPreviousChatMessage(long roomId, long userId);

    void deleteChatRoom(long roomId, long userId);
}