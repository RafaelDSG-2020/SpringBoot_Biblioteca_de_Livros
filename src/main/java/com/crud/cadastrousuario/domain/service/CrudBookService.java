package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.api.controller.BookController;
import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.BookRepository;
import com.crud.cadastrousuario.domain.repository.BookRepositorySpec;
import com.crud.cadastrousuario.domain.repository.UserRepository;
import com.crud.cadastrousuario.domain.repository.UserRepositorySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class CrudBookService {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    private Mapper bookMapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    public List<Book> findUser(Pageable pageable, BookDTO filter) {

        LOGGER.info("Executed the process of searching for user paged books in the database, paeable={} ", pageable);
        Page<Book> pageBook = bookRepository.findAll(
                BookRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Book> books = pageBook.getContent();

        return bookMapper.toDTO(books, Book.class);

    }

    public Book findUserByID(Long id) {

        LOGGER.info("Executed the process of searching for book by id in the database");

        Optional<Book> opt = bookRepository.findById(id);
        Book bookSave = opt.get();

        return bookMapper.toDTO(bookSave, Book.class);
    }
}
