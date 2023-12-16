package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityDetailDTO;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Scrap;
import com.likelion.boomarble.domain.review.domain.Review;
import com.likelion.boomarble.domain.review.exception.ReviewNotFoundException;
import com.likelion.boomarble.domain.user.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CommunityService {
    Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO);
    CommunityListDTO getCommunityList(Country country, String university, ExType type, String semester);
    CommunityDetailDTO getCommunityDetail(long postId);
    public int scrapReview(long reviewId, long userId);
    public int unscrapReview(long reviewId, long userId);
}
