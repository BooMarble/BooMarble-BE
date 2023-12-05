package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Like;
import com.likelion.boomarble.domain.model.repository.LikeRepository;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.*;
import com.likelion.boomarble.domain.universityInfo.exception.UniversityInfoNotFoundException;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.universityInfo.specification.UniversityInfoSpecifications;
import com.likelion.boomarble.domain.user.domain.User;
import com.likelion.boomarble.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityInfoServiceImpl implements UniversityInfoService{

    private final UniversityInfoRepository universityInfoRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public UniversityInfoListDTO getUniversityInfoList(Country country, String university, ExType type) {
        Specification<UniversityInfo> spec = Specification.where(UniversityInfoSpecifications.hasCountry(country))
                .and(UniversityInfoSpecifications.hasUniversity(university))
                .and(UniversityInfoSpecifications.hasType(type));

        List<UniversityInfo> results = universityInfoRepository.findAll(spec);
        return UniversityInfoListDTO.from(results);
    }
    @Override
    @Transactional
    public UniversityInfoDetailDTO getUniversityInfoDetail(long universityId) {
        UniversityInfo universityInfo = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학 정보가 없습니다."));
        return UniversityInfoDetailDTO.from(universityInfo);
    }
    @Override
    @Transactional
    public UniversityInfoListDTO searchUniversityInfoList(String keyword) {
        List<UniversityInfo> universityInfoList = universityInfoRepository.findAllByNameContaining(keyword);
        return UniversityInfoListDTO.from(universityInfoList);
    }
    @Override
    @Transactional
    public UniversityNameListDTO getUniversitiedByCountry(Country country) {
        List<UniversityInfo> universities = universityInfoRepository.findAllByCountry(country);
        return UniversityNameListDTO.from(universities);
    }
    @Override
    @Transactional
    public int likeUniversityInfo(long universityId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        UniversityInfo universityInfo = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학정보가 존재하지 않습니다."));
        if (likeRepository.findByUserAndUniversityInfo(user, universityInfo).isPresent()) return 409;
        if (likeRepository.save(new Like(user, universityInfo)) == null) return 400;
        return 200;
    }
    @Override
    @Transactional
    public int unlikeUniversityInfo(long universityId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다."));
        UniversityInfo universityInfo = universityInfoRepository.findById(universityId)
                .orElseThrow(() -> new UniversityInfoNotFoundException("해당 대학정보가 존재하지 않습니다."));
        Optional<Like> like = likeRepository.findByUserAndUniversityInfo(user, universityInfo);
        if (like.isPresent()){
            likeRepository.delete(like.get());
            if (likeRepository.findByUserAndUniversityInfo(user, universityInfo).isEmpty()) return 200;
            else return 400;
        } else return 404;
    }
//    @Override
//    @Transactional
//    public UniversityInfo registerUniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO) {
//        UniversityInfo universityInfo = new UniversityInfo(registerUniversityInfoDTO);
//        return universityInfoRepository.save(universityInfo);
//    }
}