package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import com.crud.cadastrousuario.domain.model.Author;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AuthorRepositorySpec {

    public static Specification<Author> filter(AuthorDTO filter) {
        return Specification
                .where(filterWhereIn("name", filter.getName()))
                .and(filterWhereIn("nationality", filter.getNationality()));
    }

    public static Specification<Author> filterWhereIn(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.like(builder.lower(root.get(field)), "%"+value.toLowerCase()+"%"));

    }

    private static List<String> prepareValuesToFilter(String value) {
        if (Objects.isNull(value)) return Collections.emptyList();
        return List.of(clearString(value));
    }

    public static String clearString(String value) {
        return value.trim().toLowerCase();
    }
}
