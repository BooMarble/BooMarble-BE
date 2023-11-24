package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CommunityViewDTO {
    private String communityTitle;
    private String communityCountry;
    private String communityType;
    private String communitySemester;

    public static CommunityViewDTO of (Community community) {
        return CommunityViewDTO.builder()
                .communityTitle(community.getTitle())
                .communityCountry(community.getCountry().getName())
                .communityType(community.getExType().getName())
                .communitySemester(community.getSemester()).build();
    }
}
