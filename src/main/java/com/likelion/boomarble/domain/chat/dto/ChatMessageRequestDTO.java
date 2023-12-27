package com.likelion.boomarble.domain.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDTO {

    // 메시지 타입 : 채팅
    public enum MessageType {
        TALK,
        UNREAD_MESSAGE_COUNT_ALARM
    }

    private Long messageId;
    private MessageType type; // 메시지 타입
    private String nickName;
    private Long roomId; // 공통으로 만들어진 방 번호
    private Long otherUserIds; // 상대방
    private String message; // 메시지
    private Long userId;
    private int count;
    private Boolean isFile;

    // 안 읽은 메세지 처리를 위한 생성자
    public ChatMessageRequestDTO(ChatMessageRequestDTO chatMessageDto, int count) {
        this.type = MessageType.UNREAD_MESSAGE_COUNT_ALARM; // 메시지 타입
        this.roomId = chatMessageDto.roomId; // 방 이름
        this.otherUserIds = chatMessageDto.otherUserIds; // 상대방 privateKey
        this.count = count; //안읽은 메세지 개수
    }
}
