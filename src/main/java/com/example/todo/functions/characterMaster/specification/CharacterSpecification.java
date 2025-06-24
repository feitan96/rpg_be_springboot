package com.example.todo.functions.characterMaster.specification;

import com.example.todo.functions.characterMaster.dto.FilterCharacter;
import com.example.todo.functions.characterMaster.entity.GameCharacter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CharacterSpecification {

    public static Specification<GameCharacter> getFilteredCharacters(FilterCharacter filter, String searchTerm) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always include only non-deleted characters
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));

            // Search term (for name field)
            if (StringUtils.hasText(searchTerm)) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + searchTerm.toLowerCase() + "%"
                ));
            }

            // Filter by type
            if (filter.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.getType()));
            }

            // Filter by classification - checking if the enum is not null instead of using hasText
            if (filter.getClassification() != null) {
                predicates.add(criteriaBuilder.equal(root.get("classification"), filter.getClassification()));
            }

            // Filter by numeric ranges for base stats
            if (filter.getMinBaseHealth() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("baseHealth"), filter.getMinBaseHealth()));
            }
            if (filter.getMaxBaseHealth() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("baseHealth"), filter.getMaxBaseHealth()));
            }

            if (filter.getMinBaseAttack() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("baseAttack"), filter.getMinBaseAttack()));
            }
            if (filter.getMaxBaseAttack() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("baseAttack"), filter.getMaxBaseAttack()));
            }

            if (filter.getMinBaseMagic() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("baseMagic"), filter.getMinBaseMagic()));
            }
            if (filter.getMaxBaseMagic() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("baseMagic"), filter.getMaxBaseMagic()));
            }

            if (filter.getMinBasePhysicalDefense() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("basePhysicalDefense"), filter.getMinBasePhysicalDefense()));
            }
            if (filter.getMaxBasePhysicalDefense() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("basePhysicalDefense"), filter.getMaxBasePhysicalDefense()));
            }

            if (filter.getMinBaseMagicalDefense() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("baseMagicalDefense"), filter.getMinBaseMagicalDefense()));
            }
            if (filter.getMaxBaseMagicalDefense() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("baseMagicalDefense"), filter.getMaxBaseMagicalDefense()));
            }

            if (filter.getMinBaseSpeed() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("baseSpeed"), filter.getMinBaseSpeed()));
            }
            if (filter.getMaxBaseSpeed() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("baseSpeed"), filter.getMaxBaseSpeed()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}