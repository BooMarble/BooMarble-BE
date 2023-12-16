package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Comment;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private User writer;
    private List<CommentResponseDTO> children = new ArrayList<>();

    public CommentResponseDTO(Long id, String content, User writer) {
        this.id = id;
        this.content = content;
        this.writer = writer;
    }

    public static CommentResponseDTO convertCommentToDto(Comment comment){
        return comment.getIsDeleted() ?
                new CommentResponseDTO(comment.getId(), "삭제된 댓글입니다.", null) :
                new CommentResponseDTO(comment.getId(), comment.getContent(), comment.getWriter());
    }

}
