package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CommunityViewDTO {
    private String communityTitle;
    private List<CommunityTagMap> communityTagList;

    public static CommunityViewDTO of (Community community) {
        return CommunityViewDTO.builder()
                .communityTitle(community.getTitle())
                .communityTagList(community.getCommunityTagList()).build();
    }
}
