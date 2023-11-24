package com.likelion.boomarble.domain.universityInfo.specification;

import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import org.springframework.data.jpa.domain.Specification;
import com.likelion.boomarble.domain.universityInfo.domain.UniversityInfo;

public class UniversityInfoSpecifications {

    public static Specification<UniversityInfo> hasCountry(Country country) {
        return (root, query, criteriaBuilder) -> {
            if ("all".equals(country)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("country"), country);
        };
    }

    public static Specification<UniversityInfo> hasUniversity(String university) {
        return (root, query, criteriaBuilder) -> {
            if (university == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), university);
        };
    }

    public static Specification<UniversityInfo> hasType(ExType type) {
        return (root, query, criteriaBuilder) -> {
            if (type == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("exType"), type);
        };
    }
}
