package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> , JpaSpecificationExecutor<Author> {
    boolean existsByFlag(Integer flag);
}
