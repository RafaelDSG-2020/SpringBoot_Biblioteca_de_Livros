package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.UserDTO;

import com.crud.cadastrousuario.domain.model.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserRepositorySpec {



    public static Specification<User> filter(UserDTO filter) {
        return Specification
                .where(filterWhereIn("name", filter.getName()))
                .and(filterWhereIn("email", filter.getEmail()))
                .and(filterWhereIn("phone", filter.getPhone()))
                .and(filterFlag("flag",     filter.getFlag().toString()));
    }



    public static Specification<User> filterWhereIn(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.like(builder.lower(root.get(field)), "%"+value.toLowerCase()+"%"));
    }
    public static Specification<User> filterFlag(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.equal(root.get(field), value));
    }

    private static List<String> prepareValuesToFilter(String value) {
        if (Objects.isNull(value)) return Collections.emptyList();
        return List.of(clearString(value));
    }

    public static String clearString(String value) {
        return value.trim().toLowerCase();
    }

}
