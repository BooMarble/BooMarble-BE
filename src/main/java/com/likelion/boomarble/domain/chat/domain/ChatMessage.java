package com.likelion.boomarble.domain.chat.domain;

import com.likelion.boomarble.domain.model.Status;
import com.likelion.boomarble.domain.user.domain.BaseTimeEntity;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public ChatMessage(User user, ChatRoom chatRoom, String message) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.message = message;
        this.status = Status.ACTIVE;
    }
}