package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

public class BookRepositorySpec {



    public static Specification<Book> filter(BookDTO filter) {


        return Specification
                .where(filterWhereIn("title", filter.getTitle()))
               .and(filterWhereIn("publishingCompany", filter.getPublishingCompany()))
               .and(filterWhereDate("publishingDate", filter.getPublishingDate(), LocalDateTime.now()))
                .and(filterWhereIn("isbn", filter.getIsbn()))
                .and(flagIsNotZero("flag", "0"));
    }



    public static Specification<Book> filterWhereIn(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.like(builder.lower(root.get(field)), "%"+value.toLowerCase()+"%"));
    }

    public static Specification<Book> flagIsNotZero(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.notEqual(root.get(field), value));
    }

    public static Specification<Book> filterWhereDate(String field, LocalDateTime valueStart, LocalDateTime valueEnd) {
        if (field == null || field.trim().isEmpty() || valueStart == null || valueEnd == null) {
            return null;
        }

        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Path<LocalDateTime> dateField = root.get(field);
            return builder.between(dateField, valueStart, valueEnd);
        };
    }

    private static List<String> prepareValuesToFilter(String value) {
        if (Objects.isNull(value)) return Collections.emptyList();
        return List.of(clearString(value));
    }

    public static String clearString(String value) {
        return value.trim().toLowerCase();
    }


}
