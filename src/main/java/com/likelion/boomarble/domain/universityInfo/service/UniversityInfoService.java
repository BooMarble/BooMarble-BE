package com.likelion.boomarble.domain.universityInfo.service;

import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoDetailDTO;
import com.likelion.boomarble.domain.universityInfo.dto.UniversityInfoListDTO;

public interface UniversityInfoService {

    UniversityInfoListDTO getUniversityInfoList(String country, String university, String type);

    UniversityInfoDetailDTO getUniversityInfoDetail(long universityId);

    UniversityInfoListDTO searchUniversityInfoList(String keyword);
}