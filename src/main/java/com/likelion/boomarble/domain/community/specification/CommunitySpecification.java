package com.likelion.boomarble.domain.community.specification;

import com.likelion.boomarble.domain.community.domain.Community;
import com.likelion.boomarble.domain.model.Country;
import com.likelion.boomarble.domain.model.ExType;
import org.springframework.data.jpa.domain.Specification;

public class CommunitySpecification {

    public static Specification<Community> hasCountry(Country country){
        return (root, query, criteriaBuilder) -> {
            if (country == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("country"), country);
        };
    }

    public static Specification<Community> hasUniversity(String university){
        return (root, query, criteriaBuilder) -> {
            if (university == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("university"), university);
        };
    }

    public static Specification<Community> hasType(ExType type){
        return (root, query, criteriaBuilder) -> {
            if (type == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("exType"), type);
        };
    }

    public static Specification<Community> hasSemester(String semester){
        return (root, query, criteriaBuilder) -> {
            if (semester == null){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("semester"), semester);
        };
    }
}
