package com.likelion.boomarble.domain.chat.service;

import com.likelion.boomarble.domain.chat.domain.ChatMessage;
import com.likelion.boomarble.domain.chat.domain.ChatRoom;
import com.likelion.boomarble.domain.chat.dto.ChatMessageRequestDTO;
import com.likelion.boomarble.domain.chat.exception.ChatRoomNotFoundException;
import com.likelion.boomarble.domain.chat.repository.ChatMessageRepository;
import com.likelion.boomarble.domain.chat.repository.ChatRoomRepository;
import com.likelion.boomarble.domain.chat.repository.RedisRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final RedisRepository redisRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChannelTopic channelTopic;
    private final RedisPublisher redisPublisher;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void sendMessage(ChatMessageRequestDTO chatMessageRequest, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequest.getRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다."));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(chatMessageRequest.getMessage())
                .build();
        chatMessageRepository.save(chatMessage);

        chatMessageRequest.setNickName(user.getNickname());
        chatMessageRequest.setUserId(userId);
        OptionalLong otherUserId = chatRoom.getChatRoomUsers().stream()
                .filter(c -> c.getUser()!=user)
                .mapToLong(c -> c.getUser().getId())
                .findFirst();
        if (otherUserId.isEmpty()){
            throw new UsernameNotFoundException("다른 채팅 유저가 존재하지 않습니다.");
        }
        chatMessageRequest.setOtherUserId(otherUserId.getAsLong());
        if (chatMessageRequest.getType() == ChatMessageRequestDTO.MessageType.TALK) {
            redisPublisher.publish(channelTopic, chatMessageRequest);
            updateUnReadMessageCount(chatMessageRequest);
        }
    }
    //안읽은 메세지 업데이트
    private void updateUnReadMessageCount(ChatMessageRequestDTO chatMessageRequest) {
        Long otherUserId = chatMessageRequest.getOtherUserId();
        String roomId = String.valueOf(chatMessageRequest.getRoomId());
        if (!redisRepository.existChatRoomUserInfo(otherUserId) || !redisRepository.getUserEnterRoomId(otherUserId).equals(chatMessageRequest.getRoomId())) {
            redisRepository.addChatRoomMessageCount(roomId, otherUserId);
            int unReadMessageCount = redisRepository.getChatRoomMessageCount(roomId, otherUserId);
            ChatMessageRequestDTO messageRequest = new ChatMessageRequestDTO(chatMessageRequest, unReadMessageCount);
            redisPublisher.publish(channelTopic, messageRequest);
        }
    }
    // 채팅방 입장
    @Override
    @Transactional
    public void enter(long userId, Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다."));
        // 채팅방에 들어온 정보를 Redis 저장
        redisRepository.userEnterRoomInfo(userId, roomId);
        redisRepository.initChatRoomMessageInfo(String.valueOf(chatRoom.getId()), userId);
    }
}