package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.*;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;

public interface CommunityService {
    Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO);
    CommunityListDTO getCommunityList(Country country, String university, ExType type, String semester);
    CommunityDetailDTO getCommunityDetail(long postId);
    int updateCommunityPost(long postId, CommunityCreateDTO communityCreateDTO, long userId);
    int deleteCommunityPost(long postId, long userId);
    public int scrapCommunityPost(long reviewId, long userId);
    public int unscrapCommunityPost(long reviewId, long userId);
}
