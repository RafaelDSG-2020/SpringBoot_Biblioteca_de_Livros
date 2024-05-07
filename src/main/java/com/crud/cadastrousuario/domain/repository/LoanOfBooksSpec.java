package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import com.crud.cadastrousuario.domain.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LoanOfBooksSpec {

    public static Specification<LoanOfBooks> filter(LoanOfBooksDTO filter) {
        return Specification
                .where(filterWhereDate("dateRemoval", filter.getDateRemoval()
                        , "dateReturn" , filter.getDateReturn()));
    }



    public static Specification<LoanOfBooks> filterWhereDate(String dateRemoval, LocalDate valueStart, String dateReturn ,LocalDate valueEnd) {
        if (dateRemoval == null || dateRemoval.trim().isEmpty() || valueStart == null
                || valueEnd == null || dateReturn == null || dateReturn.trim().isEmpty() ) {
            return null;
        }

        return (Root<LoanOfBooks> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Path<LocalDate> dateRemovalField = root.<LocalDate>get(dateRemoval);
            Path<LocalDate> dateReturnField = root.<LocalDate>get(dateReturn);
            return builder.and(
                    builder.greaterThanOrEqualTo(dateRemovalField, valueStart),
                    builder.lessThanOrEqualTo(dateReturnField, valueEnd)
            );
        };
    }




}
