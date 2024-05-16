package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> , JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);

    boolean existsByFlag(Integer flag);

    Optional<Book> findByIdAndFlagEquals(Long id, Integer flag);

    Optional<Object> findByIdAndFlagEqualsAndIsbn(Long id, Integer i, String isbn);
}
