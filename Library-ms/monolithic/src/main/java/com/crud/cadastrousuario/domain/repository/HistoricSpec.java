package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.dto.HistoricDTO;
import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.model.Historic;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class HistoricSpec {

    public static Specification<Historic> filter(HistoricDTO filter) {
        return Specification
                .where(filterWhereDate("dateRemoval", filter.getDateRemoval()
                        , "dateReturn" , filter.getDateReturnExpected()));
    }



    public static Specification<Historic> filterWhereDate(String dateRemoval, LocalDate valueStart, String dateReturn , LocalDate valueEnd) {
        if (dateRemoval == null || dateRemoval.trim().isEmpty() || valueStart == null
                || valueEnd == null || dateReturn == null || dateReturn.trim().isEmpty() ) {
            return null;
        }

        return (Root<Historic> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Path<LocalDate> dateRemovalField = root.<LocalDate>get(dateRemoval);
            Path<LocalDate> dateReturnField = root.<LocalDate>get(dateReturn);
            return builder.and(
                    builder.greaterThanOrEqualTo(dateRemovalField, valueStart),
                    builder.lessThanOrEqualTo(dateReturnField, valueEnd)
            );
        };
    }

}
