package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanOfBooksRepository extends JpaRepository<LoanOfBooks, Long> {
}
