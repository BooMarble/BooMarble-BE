package com.likelion.boomarble.domain.chat.dto;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnreadMessageCount {
    private Long otherUserId;
    private int unreadCount;
    private Long roomId;
    private String type;

    public UnreadMessageCount(ChatMessageRequestDTO roomMessage) {
        this.type = "UNREAD";
        this.otherUserId = roomMessage.getOtherUserIds();
        this.roomId = roomMessage.getRoomId();
        this.unreadCount = roomMessage.getCount();
    }
}
