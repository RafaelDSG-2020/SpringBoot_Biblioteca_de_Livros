package com.crud.cadastrousuario.domain.repository;

import com.crud.cadastrousuario.domain.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
