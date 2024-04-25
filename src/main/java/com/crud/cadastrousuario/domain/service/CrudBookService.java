package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.api.controller.BookController;
import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CrudBookService {

    @Autowired
    public BookRepository bookRepository;


    public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    public List<BookDTO> findUser(Pageable pageable, BookDTO filter) {

        LOGGER.info("Executed the process of searching for user paged books in the database, paeable={} ", pageable);
        Page<Book> pageBook = bookRepository.findAll(
                BookRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Book> books = pageBook.getContent();

        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());



    }

    public BookDTO findBookByID(Long id) {

        LOGGER.info("Executed the process of searching for book by id in the database");

        Optional<Book> opt = isIdAvailable(id);
        Book bookSave = opt.get();

        return new BookDTO(bookSave);
    }

    public BookDTO save(BookDTO bookCreateDTO) {

        LOGGER.info("Executed the process of saving book to the database");

        Book book = new Book(bookCreateDTO);
        book = bookRepository.save(book);
        return new BookDTO(book);
    }

    public BookDTO updateBookByID(Long id, BookDTO bookCreateDTO) {

        LOGGER.info("Executed the process of updating book by id in the database");

        Book book = new Book(bookCreateDTO);
        isIdAvailable(id);
        book.setId(id);
        book = bookRepository.save(book);
        return new BookDTO(book);

    }

    public void deleteBookByID(Long id) {

        LOGGER.info("Executed the process of delete book by id in the database");

        Optional<Book> opt = isIdAvailable(id);
        Book bookSave = opt.get();

        bookRepository.delete(bookSave);
    }



    public Optional<Book> isIdAvailable(Long id) {

        LOGGER.info("Executed the process of validating book id in the database");
        Optional<Book> opt = bookRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Livro com id: " + id + " Inexistente.");
        }

        return opt;

    }


}
