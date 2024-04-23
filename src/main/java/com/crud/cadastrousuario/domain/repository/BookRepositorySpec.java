package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BookRepositorySpec {



    public static Specification<Book> filter(BookDTO filter) {
        return Specification
                .where(filterWhereIn("title", filter.getTitle()))
                .and(filterWhereIn("publishing_company", filter.getPublishing_company()))
                .and(filterWhereIn("published_date", filter.getPublished_date()))
                .and(filterWhereIn("ISBN", filter.getISBN()));
    }



    public static Specification<Book> filterWhereIn(String field, String value) {
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
