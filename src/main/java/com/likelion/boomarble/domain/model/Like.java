package com.likelion.boomarble.domain.model;

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
@Table(name = "likes")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "universityInfo")
    private UniversityInfo universityInfo;

    @Builder
    public Like(User user, UniversityInfo universityInfo){
        this.user = user;
        this.universityInfo = universityInfo;
    }

}