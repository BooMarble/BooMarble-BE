package com.likelion.boomarble.domain.review.dto;

import com.likelion.boomarble.domain.review.domain.Subjects;
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
public class SubjectListDTO {
    private List<SubjectDTO> subjectDTOList;

    public static SubjectListDTO from(List<Subjects> subjects){
        List<SubjectDTO> subjectDTOS = subjects.stream()
                .map(SubjectDTO::of)
                .collect(Collectors.toList());
        return new SubjectListDTO(subjectDTOS);
    }
}
