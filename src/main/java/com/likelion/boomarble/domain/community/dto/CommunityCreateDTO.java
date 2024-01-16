package com.likelion.boomarble.domain.community.dto;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityCreateDTO {
    private User postWriter;
    private String postTitle;
    private String postContent;
    private Country postCountry;
    private long postUniversityId;
    private ExType postExType;
    private String postSemester;
    private ArrayList<String> postTags;
}
