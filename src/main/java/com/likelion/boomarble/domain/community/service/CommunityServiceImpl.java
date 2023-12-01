package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.*;
import com.likelion.boomarble.domain.community.exception.CommunityNotFoundException;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.community.repository.CommunityTagRepository;
import com.likelion.boomarble.domain.community.specification.CommunitySpecification;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.model.repository.TagRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommunityTagRepository communityTagRepository;

    @Override
    @Transactional
    public CommunityListDTO getCommunityList(Country country, String university, ExType type, String semester) {
        Specification<Community> spec = Specification.where(CommunitySpecification.hasCountry(country))
                .and(CommunitySpecification.hasUniversity(university))
                .and(CommunitySpecification.hasType(type))
                .and(CommunitySpecification.hasSemester(semester));

        List<Community> communities = communityRepository.findAll(spec);
        return CommunityListDTO.from(communities);
    }

    @Override
    @Transactional
    public Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO) {
        Optional<User> user = userRepository.findById(userId);
        Community community = new Community(communityCreateDTO, user.get());
        Community result = communityRepository.save(community);
        String[] tagList = communityCreateDTO.getPostTags().split(",");
        Tag checkTag = null;
        for(String tag : tagList){
            checkTag = tagRepository.findByName(tag);
            if (checkTag != null) {
                checkTag.plusCount();
            } else {
                checkTag = new Tag(tag, 1);
                tagRepository.save(checkTag);
            }
            CommunityTagMap communityTagMap = new CommunityTagMap(community, checkTag);
            communityTagRepository.save(communityTagMap);
            community.addCommunityTagList(communityTagMap);
        }
        return result;
    }

    @Override
    @Transactional
    public CommunityDetailDTO getCommunityDetail(long postId) {
        Community community = communityRepository.findById(postId)
                .orElseThrow(() -> new CommunityNotFoundException("해당 커뮤니티 글이 없습니다."));
        return CommunityDetailDTO.from(community);
    }

//    @Override
//    @Transactional
//    public List<String> getTagList(long postId) {
//        Community community = communityRepository.findById(postId)
//                .orElseThrow(() -> new CommunityNotFoundException("해당 커뮤니티 글이 없습니다."));
//        List<CommunityTagMap> communityTagMapList = communityTagRepository.findAllByCommunity(community);
//        List<String> tagList = new ArrayList<>();
//        for(CommunityTagMap communityTagMap: communityTagMapList){
//            String tag = communityTagMap.getTag().getName();
//            tagList.add(tag);
//        }
//        return tagList;
//    }
}
