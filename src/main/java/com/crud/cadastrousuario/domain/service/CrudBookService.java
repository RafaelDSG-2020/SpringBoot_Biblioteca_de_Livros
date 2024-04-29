package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.BookRepository;
import com.crud.cadastrousuario.domain.repository.BookRepositorySpec;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CrudBookService {

    @Autowired
    public BookRepository bookRepository;




    public List<BookDTO> findBook(Pageable pageable, BookDTO filter) {

        log.info("Executed the process of searching for user paged books in the database, paeable={} ", pageable);
        Page<Book> pageBook = bookRepository.findAll(
                BookRepositorySpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Book> books = pageBook.getContent();

        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());



    }

    public BookDTO findBookByID(Long id) {

        log.info("Executed the process of searching for book by id in the database");

        Optional<Book> opt = isIdAvailable(id);
        Book bookSave = opt.get();
        isFlagAvailable(bookSave);

        return new BookDTO(bookSave);
    }

    public BookDTO save(BookDTO bookCreateDTO) {

        log.info("Executed the process of saving book to the database");

        Book book = new Book(bookCreateDTO);
        isIsbnAvailable(book);
        isDateAvailable(book);
        book.setFlag("1");
        book = bookRepository.save(book);
        return new BookDTO(book);
    }

    public BookDTO updateBookByID(Long id, BookDTO bookCreateDTO) {

        log.info("Executed the process of updating book by id in the database");

        Book book = new Book(bookCreateDTO);
        isIdAvailable(id);
        isIsbnAvailable(book);
        isDateAvailable(book);
        isFlagAvailable(book);
        book.setId(id);
        book = bookRepository.save(book);
        return new BookDTO(book);

    }

    public void deleteBookByID(Long id) {

        log.info("Executed the process of delete book by id in the database");

        Optional<Book> opt = isIdAvailable(id);
        Book bookSave = opt.get();
        isFlagAvailable( bookSave);
        bookSave.setFlag("0");
        bookRepository.save(bookSave);
    }



    public Optional<Book> isIdAvailable(Long id) {

        log.info("Executed the process of validating book id in the database");
        Optional<Book> opt = bookRepository.findById(id);
        if (opt.isEmpty()){
            throw new NotFoundException("Book with id: " + id + " does not exist.");
        }

        return opt;

    }

    private void isIsbnAvailable(Book book) {

        log.info("Executed the process of validating book isbn numbers in the database");
        if (bookRepository.existsByIsbn(book.getIsbn())){
            throw new BadRequestException("Book with registered isbn");
        }
    }

    private void isFlagAvailable(Book book) {

        log.info("Executed the process of validating book Flag numbers in the database");
        if (bookRepository.existsByFlag(book.getFlag())){
            throw new BadRequestException("Book with  flag disabled ");
        }
    }

    private void isDateAvailable(Book book){

        LocalDateTime publishingDate = book.getPublishingDate();
        LocalDateTime currentDate = LocalDateTime.now();
        long daysDifference = ChronoUnit.DAYS.between(publishingDate, currentDate);
        long hoursDifference = ChronoUnit.HOURS.between(publishingDate, currentDate);
        if (daysDifference < 0){
            throw new BadRequestException("the Publication date has a value greater than the current date");
        } else if (hoursDifference < 0) {
            throw new BadRequestException("the Publication date has a value greater than the current date");
        }
    }




}
