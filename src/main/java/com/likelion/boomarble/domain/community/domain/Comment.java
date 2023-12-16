package com.likelion.boomarble.domain.community.domain;

import com.likelion.boomarble.domain.community.dto.CommentRequestDTO;
import com.likelion.boomarble.domain.user.domain.BaseTimeEntity;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=1000)
    private String content;

    @ColumnDefault("false")
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public Comment(CommentRequestDTO commentRequestDTO){
        this.content = commentRequestDTO.getContent();
    }

    public void updateWriter(User user) {
        this.writer = user;
    }

    public void updateCommunity(Community community) {
        this.community = community;
    }

    public void updateParent(Comment comment) {
        this.parent = comment;
    }

    public void changeIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


}
