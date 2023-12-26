package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunitySearchDTO {
    private String writerNickname;
    private String title;
    private String content;
    private List<String> communityTagList;

    public static CommunitySearchDTO from(Community community) {
        return CommunitySearchDTO.builder()
                .writerNickname(community.getWriter().getNickname())
                .title(community.getTitle())
                .content(community.getContent())
                .communityTagList(community.getCommunityTagList())
                .build();
    }
}
