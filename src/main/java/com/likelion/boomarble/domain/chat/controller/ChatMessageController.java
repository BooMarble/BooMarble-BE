package com.likelion.boomarble.domain.chat.controller;

import com.likelion.boomarble.domain.chat.dto.ChatMessageRequestDTO;
import com.likelion.boomarble.domain.chat.service.ChatMessageService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import com.likelion.boomarble.domain.user.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final JwtTokenProvider jwtTokenProvider;
    /**
     * websocket "/app/chat/enter"로 들어오는 메시징을 처리한다.
     * 채팅방에 입장했을 경우
     */
    @MessageMapping("/chat/enter")
    public void enter(ChatMessageRequestDTO chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        chatMessageService.enter(getUserPk(headerAccessor), chatMessageRequest.getRoomId());
    }

    /**
     * websocket "/app/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageRequestDTO chatMessageRequest, SimpMessageHeaderAccessor headerAccessor) {
        chatMessageService.sendMessage(chatMessageRequest, getUserPk(headerAccessor));
    }
    public long getUserPk(SimpMessageHeaderAccessor headerAccessor){
        String token = headerAccessor.getFirstNativeHeader("X-AUTH-TOKEN");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}