package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CommunityListDTO {
    private List<CommunityViewDTO> communityLists;

    public static CommunityListDTO from(List<Community> communities) {
        List<CommunityViewDTO> viewDTOList = communities.stream()
                .map(CommunityViewDTO::of)
                .collect(Collectors.toList());

        return new CommunityListDTO(viewDTOList);
    }

}
