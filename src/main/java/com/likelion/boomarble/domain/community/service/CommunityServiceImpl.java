package com.likelion.boomarble.domain.community.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.community.dto.*;
import com.likelion.boomarble.domain.community.exception.CommunityNotFoundException;
import com.likelion.boomarble.domain.community.repository.CommunityRepository;
import com.likelion.boomarble.domain.community.repository.CommunityTagRepository;
import com.likelion.boomarble.domain.community.specification.CommunitySpecification;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Scrap;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.model.repository.ScrapRepository;
import com.likelion.boomarble.domain.model.repository.TagRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommunityTagRepository communityTagRepository;
    private final UniversityInfoRepository universityInfoRepository;
    private final ScrapRepository scrapRepository;
    private final CommentService commentService;

    @Override
    @Transactional
    public Community createCommunityPost(Long userId, CommunityCreateDTO communityCreateDTO) {
        Optional<User> user = userRepository.findById(userId);
        long universityId = communityCreateDTO.getPostUniversityId();
        UniversityInfo universityInfo = universityInfoRepository.findById(universityId).orElse(null);

        Community community = new Community(communityCreateDTO, universityInfo, user.get());
        Community result = communityRepository.save(community);

        String semester = community.getSemester();
        UniversityInfo university = community.getUniversity();
        Country country = community.getCountry();
        ExType exType = community.getExType();

        // 태그에 학기, 대학, 나라, 교환유형 추가
        ArrayList<String> tagList = new ArrayList(
                Arrays.asList(communityCreateDTO.getPostTags().split(",")));

        if(university != null) { tagList.add(0, university.getName()); }
        if(country != null) { tagList.add(0, country.getName()); }
        if(exType != null) { tagList.add(0, exType.getName()); }
        if(semester != null) { tagList.add(0, semester); }

        // 해시태그 추가
        for(String tag : tagList){

            Tag checkTag = tagRepository.findByName(tag);
            if (checkTag != null) {
                checkTag.plusCount();
            } else {
                checkTag = new Tag(tag, 1);
                tagRepository.save(checkTag);
            }
            CommunityTagMap communityTagMap = new CommunityTagMap(community, checkTag);
            communityTagRepository.save(communityTagMap);
        }
        return result;
    }

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
    public CommunityDetailDTO getCommunityDetail(long postId) {
        Community community = communityRepository.findById(postId)
                .orElseThrow(() -> new CommunityNotFoundException("해당 커뮤니티 글이 없습니다."));
        List<String> tagList = getTagList(postId);
        return CommunityDetailDTO.from(community, tagList);
    }

    @Transactional
    public List<String> getTagList(long postId) {
        Community community = communityRepository.findById(postId)
                .orElseThrow(() -> new CommunityNotFoundException("해당 커뮤니티 글이 없습니다."));
        List<CommunityTagMap> communityTagList = community.getCommunityTagList();
        List<String> tagList = new ArrayList<>();
        for(CommunityTagMap tag : communityTagList){
            tagList.add(tag.getTag().getName().toString());
        }
        return tagList;
    }

    @Override
    @Transactional
    public int updateCommunityPost(long postId, CommunityCreateDTO communityCreateDTO, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
        communityRepository.findById(postId).orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
        Community post = communityRepository.findByIdAndWriter(postId, user)
                .orElseThrow(() -> new NotFoundException("해당 글의 유저가 아닙니다."));
        if (post != null) {
            post.updateTitleAndContent(communityCreateDTO.getPostTitle(), communityCreateDTO.getPostContent());
            return 200;
        } else return 400;
    }

    @Override
    @Transactional
    public int deleteCommunityPost(long postId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
        communityRepository.findById(postId).orElseThrow(() -> new CommunityNotFoundException("해당 게시글이 존재하지 않습니다."));
        Optional<Community> post = communityRepository.findByIdAndWriter(postId, user);
        if (post.isPresent()){
            communityRepository.delete(post.get());
            if (communityRepository.findByIdAndWriter(postId, user).isEmpty()) return 200;
            else return 400;
        } else return 404;
    }

    // Scrap
    @Override
    @Transactional
    public int scrapCommunityPost(long postId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Community post = communityRepository.findById(postId)
                .orElseThrow(() -> new CommunityNotFoundException("해당 게시글이 존재하지 않습니다."));
        if (scrapRepository.findByUserAndCommunity(user, post).isPresent()) return 409;
        if (scrapRepository.save(new Scrap(user, null, post, 1)) == null) return 400;
        return 200;
    }

    @Override
    @Transactional
    public int unscrapCommunityPost(long postId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        Community post = communityRepository.findById(postId)
                .orElseThrow(() -> new CommunityNotFoundException("해당 게시글이 존재하지 않습니다."));
        Optional<Scrap> scrap = scrapRepository.findByUserAndCommunity(user, post);
        if (scrap.isPresent()){
            scrapRepository.delete(scrap.get());
            if (scrapRepository.findByUserAndCommunity(user,post).isEmpty()) return 200;
            else return 400;
        } else return 404;
    }
}
