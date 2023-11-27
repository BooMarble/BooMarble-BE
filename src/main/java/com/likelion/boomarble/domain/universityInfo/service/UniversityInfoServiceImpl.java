package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.*;
import com.likelion.boomarble.domain.universityInfo.exception.UniversityInfoNotFoundException;
import com.likelion.boomarble.domain.universityInfo.repository.UniversityInfoRepository;
import com.likelion.boomarble.domain.universityInfo.specification.UniversityInfoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityInfoServiceImpl implements UniversityInfoService{

    private final UniversityInfoRepository universityInfoRepository;
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
    public UniversityInfo registerUniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO) {
        return new UniversityInfo(registerUniversityInfoDTO);
    }

    @Override
    @Transactional
    public UniversityNameListDTO getUniversitiedByCountry(Country country) {
        List<UniversityInfo> universities = universityInfoRepository.findAllByCountry(country);
        return UniversityNameListDTO.from(universities);
    }
}