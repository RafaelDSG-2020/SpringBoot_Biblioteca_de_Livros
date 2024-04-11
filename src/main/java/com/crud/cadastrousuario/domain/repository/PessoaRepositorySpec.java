package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.PessoaFilterDTO;
import com.crud.cadastrousuario.domain.model.Pessoa;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PessoaRepositorySpec {


    private static CriteriaBuilder criteriaBuilder;

    public static Specification<Pessoa> filter(PessoaFilterDTO filter) {
        return Specification
                .where(filterWhereIn("nome", filter.getName()))
                .and(filterWhereIn("email", filter.getEmail()))
                .and(filterWhereIn("telefone", filter.getPhone()));
    }

//    public static Specification<Pessoa> filterWhereIn(String field, List<String> values) {
//        if (field == null || Strings.isBlank(field) || values.isEmpty()) return null;
//        return (root, query, builder) -> builder.lower(root.get(field)).in(values);
//    }

    public static Specification<Pessoa> filterWhereIn(String field, String value) {
        if (field == null || field.trim().isEmpty() || value == null || value.trim().isEmpty()) {
            return null;
        }

        return ((root, query, builder) -> builder.like(builder.lower(root.get(field)), "%"+value.toLowerCase()+"%"));
//        return (root, query, builder) -> {builder.like(builder.lower())
////            List<Predicate> predicates = new ArrayList<>();
//
////            for (String value : values) {
////                predicates.add(builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
////            }
//
//            return builder.or(predicates.toArray(new Predicate[0]));
//        };
    }

    private static List<String> prepareValuesToFilter(String value) {
        if (Objects.isNull(value)) return Collections.emptyList();
        return List.of(clearString(value));
    }

    public static String clearString(String value) {
        return value.trim().toLowerCase();
    }

}
