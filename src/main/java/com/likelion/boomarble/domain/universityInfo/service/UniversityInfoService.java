package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.universityInfo.dto.RegisterUniversityInfoDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoDetailDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;

public interface UniversityInfoService {

    UniversityInfoListDTO getUniversityInfoList(Country country, String university, ExType type);

    UniversityInfoDetailDTO getUniversityInfoDetail(long universityId);

    UniversityInfoListDTO searchUniversityInfoList(String keyword);

    UniversityInfo registerUniversityInfo(RegisterUniversityInfoDTO registerUniversityInfoDTO);
}