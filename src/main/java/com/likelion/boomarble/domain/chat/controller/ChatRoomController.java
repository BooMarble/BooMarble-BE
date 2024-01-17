package com.likelion.boomarble.domain.chat.controller;

import com.likelion.boomarble.domain.chat.dto.CreateChatRoomDTO;
import com.likelion.boomarble.domain.chat.dto.CreateResponseChatRoomDTO;
import com.likelion.boomarble.domain.chat.repository.RedisRepository;
import com.likelion.boomarble.domain.chat.service.ChatRoomService;
import com.likelion.boomarble.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final RedisRepository redisRepository;

    @PostMapping("")
    public ResponseEntity createChatRoom(Authentication authentication, @RequestBody CreateChatRoomDTO createChatRoomDTO) {
        long userId = getUserPk(authentication);
        Long chatRoomId = chatRoomService.createChatRoom(userId, createChatRoomDTO);
        Long chatPartnerUserId = createChatRoomDTO.getAnotherUserId();
        CreateResponseChatRoomDTO createResponseChatRoomDTO = CreateResponseChatRoomDTO.builder().roomId(chatRoomId).build();

        // 일대일 채팅은 안읽은 갯수 필요
        redisRepository.initChatRoomMessageInfo(String.valueOf(chatRoomId), userId);
        redisRepository.initChatRoomMessageInfo(String.valueOf(chatRoomId), chatPartnerUserId);

        return ResponseEntity.ok(createResponseChatRoomDTO);
    }

    @GetMapping("")
    public ResponseEntity getChatRoomList(Authentication authentication, Pageable pageable) {
        return ResponseEntity.ok(chatRoomService.getChatRoomList(getUserPk(authentication), pageable));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity getMessages(Authentication authentication, @PathVariable long roomId) {
        return ResponseEntity.ok(chatRoomService.getPreviousChatMessage(roomId, getUserPk(authentication)));
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity deleteChatRoom(Authentication authentication, @PathVariable long roomId) {
        chatRoomService.deleteChatRoom(roomId, getUserPk(authentication));
        return ResponseEntity.ok().build();
    }

    public long getUserPk(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUserPk();
    }
}