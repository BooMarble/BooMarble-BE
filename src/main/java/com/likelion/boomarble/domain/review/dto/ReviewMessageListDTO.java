package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessageListDTO {
    private List<ReviewMessageDTO> reviewMessageDTOList;

    public static ReviewMessageListDTO from(List<Review> reviews, long currentUserId){
        List<ReviewMessageDTO> messageDTOList = reviews.stream()
                .map(review -> ReviewMessageDTO.of(review, currentUserId))
                .collect(Collectors.toList());
        return new ReviewMessageListDTO(messageDTOList);
    }
}
