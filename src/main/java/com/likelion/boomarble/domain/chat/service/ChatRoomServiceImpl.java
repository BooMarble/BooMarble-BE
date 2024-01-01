package com.likelion.boomarble.domain.chat.service;

import com.likelion.boomarble.domain.chat.domain.ChatMessage;
import com.likelion.boomarble.domain.chat.domain.ChatRoom;
import com.likelion.boomarble.domain.chat.domain.ChatRoomUser;
import com.likelion.boomarble.domain.chat.dto.ChatRoomListDTO;
import com.likelion.boomarble.domain.chat.dto.CreateChatRoomDTO;
import com.likelion.boomarble.domain.chat.dto.GetChatMessageResponseDTO;
import com.likelion.boomarble.domain.chat.exception.ChatRoomCannotMakeRoomAloneException;
import com.likelion.boomarble.domain.chat.exception.ChatRoomForbidden;
import com.likelion.boomarble.domain.chat.exception.ChatRoomNotFoundException;
import com.likelion.boomarble.domain.chat.repository.ChatMessageRepository;
import com.likelion.boomarble.domain.chat.repository.ChatRoomRepository;
import com.likelion.boomarble.domain.chat.repository.ChatRoomUserRepository;
import com.likelion.boomarble.domain.chat.repository.RedisRepository;
import com.likelion.boomarble.domain.model.Status;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import com.likelion.boomarble.global.util.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisRepository redisRepository;

    @Override
    @Transactional
    public Long createChatRoom(long userId, CreateChatRoomDTO createChatRoomDTO) {
        if(createChatRoomDTO.getAnotherUserId() == userId) {
            throw new ChatRoomCannotMakeRoomAloneException("자기 자신과의 채팅방은 생성할 수 없습니다.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        User anotherUser = userRepository.findById(createChatRoomDTO.getAnotherUserId())
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        // 채팅방 룸 해시코드 생성
        int roomHashCode = createRoomHashCode(userId, anotherUser.getId());
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByRoomHashCodeAndStatus(roomHashCode, Status.ACTIVE);

        if (existingRoom.isPresent()) {
            ChatRoom savedChatRoom = existingRoom.get();
            organizeChatRoom(savedChatRoom, user, anotherUser);
            return savedChatRoom.getId();
        }

        ChatRoom room = ChatRoom.builder()
                .roomHashCode(roomHashCode).build();
        chatRoomRepository.save(room);
        chatRoomUserRepository.save(new ChatRoomUser(user, anotherUser.getNickname(), room));
        chatRoomUserRepository.save(new ChatRoomUser(anotherUser, user.getNickname(), room));

        return room.getId();
    }

    private int createRoomHashCode(long userId, long anotherUserId) {
        return userId > anotherUserId ? Objects.hash(userId, anotherUserId) : Objects.hash(anotherUserId, userId);
    }

    private void organizeChatRoom(ChatRoom chatRoom, User user, User anotherUser) {
        List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();
        if (chatRoomUsers.size() == 1) {
            User existingUser = chatRoomUsers.get(0).getUser();
            User missingUser = existingUser.getId() == user.getId() ? anotherUser : user;
            chatRoomUserRepository.save(new ChatRoomUser(missingUser, existingUser.getNickname(), chatRoom));
        }
    }

    @Override
    @Transactional
    public List<ChatRoomListDTO> getChatRoomList(long userId, Pageable pageable) {
        return chatRoomUserRepository
                .findAllByUserIdAndStatus(userId, Status.ACTIVE, pageable)
                .stream()
                .map(this::createChatRoomDto)
                .collect(Collectors.toList());
    }

    private ChatRoomListDTO createChatRoomDto(ChatRoomUser chatRoomUser) {
        String roomName = chatRoomUser.getName();
        Long chatRoomId = chatRoomUser.getChatRoom().getId();
        String roomId = chatRoomId.toString();
        int unReadMessageCount = redisRepository.getChatRoomMessageCount(roomId, chatRoomUser.getUser().getId());

        Optional<ChatMessage> lastMessageOpt = chatMessageRepository
                .findFirstByStatusAndChatRoomIdOrderByCreatedAtDesc(Status.ACTIVE, chatRoomId);

        String lastMessage = lastMessageOpt.map(ChatMessage::getMessage).orElse("채팅방이 생성 되었습니다.");
        LocalDateTime lastTime = lastMessageOpt.map(ChatMessage::getCreatedAt).orElse(LocalDateTime.now());

        long dayBeforeTime = ChronoUnit.MINUTES.between(lastTime, LocalDateTime.now());
        String dayBefore = Calculator.time(dayBeforeTime);

        return new ChatRoomListDTO(roomName, roomId, lastMessage, lastTime, dayBefore, unReadMessageCount);
    }

    @Override
    @Transactional
    public List<GetChatMessageResponseDTO> getPreviousChatMessage(long roomId, long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStatus(roomId, Status.ACTIVE)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다."));

        boolean isUserInChatRoom = chatRoom.getChatRoomUsers().stream()
                .anyMatch(chatRoomUser -> chatRoomUser.getUser().getId() == userId);

        if (!isUserInChatRoom) throw new ChatRoomForbidden("잘못된 접근입니다.");

        return chatMessageRepository.findAllByStatusAndChatRoomIdOrderByCreatedAtDesc(Status.ACTIVE, roomId)
                .stream()
                .map(GetChatMessageResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteChatRoom(long roomId, long userId) {
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStatus(roomId, Status.ACTIVE)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 채팅방이 존재하지 않습니다."));
        List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();
        if (!chatRoomUsers.isEmpty()) {
            chatRoomUserRepository.saveAll(chatRoomUsers.stream()
                    .map(ChatRoomUser::inActive)
                    .collect(Collectors.toList()));
        }
        chatRoomRepository.save(chatRoom.inActive());
    }
}