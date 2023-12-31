package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;
import com.likelion.boomarble.domain.user.domain.User;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@AllArgsConstructor @NoArgsConstructor
public class ReviewCommonDTO {

    private User writer;
    private boolean yesWriter;
    private long reviewId;

}
