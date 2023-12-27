package com.likelion.boomarble.domain.chat.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.boomarble.domain.chat.domain.ChatMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChatMessageResponseDTO {
    private Long userId;
    private String nickname;
    private String message; // type이 image일 경우 객체 URL이 담김
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private Boolean isFile;

    public GetChatMessageResponseDTO(ChatMessage chatMessage) {
        this.userId = chatMessage.getUser().getId();
        this.nickname = chatMessage.getUser().getNickname();
        this.message = chatMessage.getMessage();
        this.createdAt = chatMessage.getCreatedAt();
    }

    public GetChatMessageResponseDTO(ChatMessageRequestDTO request) {
        this.userId = request.getUserId();
        this.nickname = request.getNickName();
        this.message = request.getMessage();
        this.isFile = request.getIsFile();
        this.createdAt = LocalDateTime.now(); // 현재시간 저장
    }
}