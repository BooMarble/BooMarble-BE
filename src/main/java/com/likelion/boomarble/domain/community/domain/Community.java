package com.likelion.boomarble.domain.community.domain;

import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.model.Tag;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor @NoArgsConstructor
public class Community {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;
    private String title;
    private String content;
    private String semester;
    private ExType exType;
    private Country country;
    @ManyToOne
    @JoinColumn(name = "university")
    private UniversityInfo university;

//    @Builder
//    public Community(User writer, String title, String content, String semester, ExType exType, Country country, UniversityInfo university) {
//        this.writer = writer;
//        this.title = title;
//        this.content = content;
//        this.semester = semester;
//        this.exType = exType;
//        this.country = country;
//        this.university = university;
//    }

    public Community(CommunityCreateDTO communityCreateDTO, User user){
        this.writer = user;
        this.title = communityCreateDTO.getPostTitle();
        this.content = communityCreateDTO.getPostContent();
        this.country = communityCreateDTO.getPostCountry();
        this.university = communityCreateDTO.getPostUniversity();
        this.exType = communityCreateDTO.getPostExType();
        this.semester = communityCreateDTO.getPostSemester();
    }


}