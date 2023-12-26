package com.likelion.boomarble.domain.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.boomarble.domain.community.dto.CommunityCreateDTO;
import com.likelion.boomarble.domain.community.dto.CommunityTagMap;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Column(length = 2048)
    private String content;
    private String semester;
    private ExType exType;
    private Country country;
    @ElementCollection
    private List<String> communityTagList;
    @ManyToOne
    @JoinColumn(name = "university")
    private UniversityInfo university;
    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<CommunityTagMap> communityTagMapList;
    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Comment> commentList;

    public Community(CommunityCreateDTO communityCreateDTO, UniversityInfo university, User user){
        this.writer = user;
        this.title = communityCreateDTO.getPostTitle();
        this.content = communityCreateDTO.getPostContent();
        this.country = communityCreateDTO.getPostCountry();
        this.university = university;
        this.exType = communityCreateDTO.getPostExType();
        this.semester = communityCreateDTO.getPostSemester();
    }

    public void updateTitleAndContent(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void setCommunityTagList(List<String> communityTagList) {
        this.communityTagList = communityTagList;
    }
}