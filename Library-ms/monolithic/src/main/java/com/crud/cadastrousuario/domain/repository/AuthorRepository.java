package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> , JpaSpecificationExecutor<Author> {
    boolean existsByFlag(Integer flag);

    Optional<Author> findByIdAndFlagEquals(Long id, Integer flag);
}
