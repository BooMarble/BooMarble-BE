package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDetailDTO {
    private User communityWriter;
    private String title;
    private String content;
    private List<String> communityTagList;

    public static CommunityDetailDTO from(Community community) {

        return CommunityDetailDTO.builder()
                .communityWriter(community.getWriter())
                .title(community.getTitle())
                .content(community.getContent())
                .communityTagList(community.getCommunityTagList())
                .build();
    }
}
