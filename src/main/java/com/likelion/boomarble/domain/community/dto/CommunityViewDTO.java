package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CommunityViewDTO {
    private long communityId;
    private String communityTitle;
    private List<String> communityTagList;

    public static CommunityViewDTO of (Community community) {
        return CommunityViewDTO.builder()
                .communityId(community.getId())
                .communityTitle(community.getTitle())
                .communityTagList(community.getCommunityTagList()).build();
    }
}
