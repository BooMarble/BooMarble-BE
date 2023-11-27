package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.*;

import java.util.List;

public interface UniversityInfoService {

    UniversityInfoListDTO getUniversityInfoList(Country country, String university, ExType type);

    UniversityInfoDetailDTO getUniversityInfoDetail(long universityId);

    UniversityInfoListDTO searchUniversityInfoList(String keyword);

    UniversityInfo registerUniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO);

    UniversityNameListDTO getUniversitiedByCountry(Country country);
}