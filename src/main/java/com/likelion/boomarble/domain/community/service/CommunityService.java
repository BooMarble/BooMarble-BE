package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityDetailDTO;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;

import java.util.List;

public interface CommunityService {
    Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO);
    CommunityListDTO getCommunityList(Country country, String university, ExType type, String semester);
    CommunityDetailDTO getCommunityDetail(long postId);
//    List<String> getTagList(long postId);
}
