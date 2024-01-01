package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.dto.CountryListDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoDetailDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;

import java.util.List;

public interface UniversityInfoService {

    UniversityInfoListDTO getUniversityInfoList(Country country, String university, ExType type);

    UniversityInfoDetailDTO getUniversityInfoDetail(long universityId);

    UniversityInfoListDTO searchUniversityInfoList(String keyword);

    CountryListDTO getUniversityByCountry();

    int likeUniversityInfo(long universityId, long userId);

    int unlikeUniversityInfo(long universityId, long userId);

    List<String> searchCountries(String query);

    List<String> searchUniversities(String query);

    List<String> searchExchangeTypes(String query);
}