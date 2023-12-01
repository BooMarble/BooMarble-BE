package com.likelion.boomarble.domain.community.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @Entity
@NoArgsConstructor
@AllArgsConstructor
public class CommunityTagMap {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public CommunityTagMap(Community community, Tag tag){
        this.community = community;
        this.tag = tag;
    }
}
