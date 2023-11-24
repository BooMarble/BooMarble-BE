package com.likelion.boomarble.domain.community.service;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityListDTO;
import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.community.repository.CommunityTagRepository;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.model.repository.TagRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public CommunityListDTO getCommunityList(String title, String country, String type, String semester) {
        List<Community> communities = communityRepository.findAll();
        return CommunityListDTO.from(communities);
    }

    @Override
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
            communityTagRepository.save(new CommunityTagMap(community, checkTag));
        }
        return result;
    }
}
