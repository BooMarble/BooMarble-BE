package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;

public interface CommunityService {
    Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO);
    CommunityListDTO getCommunityList(String title, String country, String type, String semester);

}
