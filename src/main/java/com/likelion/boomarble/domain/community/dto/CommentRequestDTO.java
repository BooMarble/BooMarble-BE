package com.likelion.boomarble.domain.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDTO {
    private Long userId;
    private Long parentId;
    private String content;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CommentRequestDTO(String content){
        this.content = content;
    }
}
