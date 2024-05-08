package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.repository.BookRepository;
import com.crud.cadastrousuario.domain.repository.BookRepositorySpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CrudBookService {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public CrudAuthorService authorService;

    private final Integer STATUS_FLAG_ACTIVE = 1;

    private final Integer STATUS_FLAG_INACTIVE = 0;

    private final Integer MINIMUM_VALUE_OF_DAYS = 0;







    public List<BookDTO> findBook(Pageable pageable, BookDTO filter) {

        log.info("Executed the process of searching for user paged books in the database, paeable={} ", pageable);
        Page<Book> pageBook = bookRepository.findAll(
                BookRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Book> books = pageBook.getContent();

        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());



    }

    public BookDTO findBookByIDActive(Long id) {

        log.info("Executed the process of searching for book by id in the database");

        Book bookSave = isIdandFlagActive(id);
        return new BookDTO(bookSave);
    }

    public BookDTO save(BookDTO bookCreateDTO) {

        log.info("Executed the process of saving book to the database");

        Book book = new Book(bookCreateDTO);
        isIsbnAvailable(book);
        isDateAvailable(book);
        authorService.isAuthorAvailable();
        book = bookRepository.save(book);
        return new BookDTO(book);
    }



    public BookDTO updateBookActive(Long id, BookDTO bookCreateDTO) {

        log.info("Executed the process of updating book by id in the database");

        Book book = new Book(bookCreateDTO);
        isIdAndFlagAndIsbnActive(id , book);
        isDateAvailable(book);
        book.setId(id);
        book = bookRepository.save(book);
        return new BookDTO(book);

    }

    public void deleteBookByID(Long id) {

        log.info("Executed the process of delete book by id in the database");

        Book bookSave = isIdandFlagActive(id);
        bookSave.setFlag(STATUS_FLAG_INACTIVE);
        bookRepository.save(bookSave);
    }



    public Book isIdandFlagActive(Long id) {

        log.info("Executed the process of validating book id in the database");

        return bookRepository.findByIdAndFlagEquals(id , STATUS_FLAG_ACTIVE)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " does not exist or Flag inactive."));

    }

    private void isIsbnAvailable(Book book) {

        log.info("Executed the process of validating book isbn numbers in the database");

        if (bookRepository.existsByIsbn(book.getIsbn())){
            throw new BadRequestException("Book with registered isbn");
        }
    }

    private void isIdAndFlagAndIsbnActive(Long id ,  Book book) {

        log.info("Executed the process of validating book Active ID,Flag,Isbn numbers in the database");

        bookRepository.findByIdAndFlagEqualsAndIsbn(id, STATUS_FLAG_ACTIVE, book.getIsbn())
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " does not exist or Flag inactive."));

    }

    private void isDateAvailable(Book book){

        LocalDate publishingDate = book.getPublishingDate();
        LocalDateTime currentDate = LocalDateTime.now();
        long daysDifference = ChronoUnit.DAYS.between(publishingDate, currentDate);

        if (daysDifference < MINIMUM_VALUE_OF_DAYS){
            throw new BadRequestException("the Publication date has a value greater than the current date");
        }
    }




}
