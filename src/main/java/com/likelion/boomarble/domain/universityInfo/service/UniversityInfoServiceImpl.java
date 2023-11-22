package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;
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
    public UniversityInfoListDTO getUniversityInfoList(String countryStr, String university, String type) {
        Country country = null;
        if (countryStr != null && !countryStr.equals("all")) country = Country.valueOf(countryStr);
        ExType exType = null;
        if (type != null) exType = ExType.valueOf(type);

        Specification<UniversityInfo> spec = Specification.where(UniversityInfoSpecifications.hasCountry(country))
                .and(UniversityInfoSpecifications.hasUniversity(university))
                .and(UniversityInfoSpecifications.hasType(exType));

        List<UniversityInfo> results = universityInfoRepository.findAll(spec);
        return UniversityInfoListDTO.from(results);
    }
}
