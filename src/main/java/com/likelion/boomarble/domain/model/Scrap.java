package com.likelion.boomarble.domain.model;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "review")
    private Review review;
    @ManyToOne
    @JoinColumn(name = "community")
    private Community community;
    private int type;

    @Builder
    public Scrap(User user, Review review, Community community, int type){
        this.user = user;
        this.review = review;
        this.community = community;
        this.type = type;
    }

}