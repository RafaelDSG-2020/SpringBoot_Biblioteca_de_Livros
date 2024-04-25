package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.BookDTO;

import com.crud.cadastrousuario.domain.service.CrudBookService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@Log4j2
public class BookController {

    @Autowired
    private CrudBookService bookService;


    public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);


    @GetMapping
    public ResponseEntity<List<BookDTO>> findBookByParameters(@PageableDefault(size = 5) Pageable pageable, BookDTO filter) {

        log.info("Method: findUserByParameters searches for a set of paginated books 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<BookDTO> books = bookService.findUser(pageable , filter);
        log.info("HTTP Method: GET Endpoint: api/v1/books  payload = {} elapsedTime = {} ms", filter , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(books);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findBookByID(@PathVariable(value = "id") Long id){

        LOGGER.info("Method: findUserByID searches for just one book by their ID. HTTP Method: GET");
        long start = System.currentTimeMillis();
        BookDTO bookSave = bookService.findBookByID(id);
        log.info("HTTP Method: GET Endpoint: api/v1/books/{id}  payload = {} elapsedTime = {} ms", id , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(bookSave);

    }



    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookDTO bookCreateDTO){

        LOGGER.info("Method: saveUser creates a book in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        BookDTO bookSave = bookService.save(bookCreateDTO);
        log.info("HTTP Method: POST Endpoint: api/v1/books  payload = {} elapsedTime = {} ms", bookSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid BookDTO bookCreateDTO) {

        LOGGER.info("Method: update User has the function of updating a book created in a table. HTTP Method: PUT");
        long start = System.currentTimeMillis();
        BookDTO bookSave = bookService.updateBookByID(id, bookCreateDTO);
        log.info("HTTP Method: PUT Endpoint: api/v1/books/{id}  payload = {} elapsedTime = {} ms", id , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(bookSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        LOGGER.info("Method: deleteUserByID deletes a book by ID in the database. HTTP Method: DELETE ");
        long start = System.currentTimeMillis();
        bookService.deleteBookByID(id);
        log.info("HTTP Method: DELETE Endpoint: api/v1/books/{id}  payload = {} elapsedTime = {} ms", id , (System.currentTimeMillis() - start));
        return ResponseEntity.noContent().build();
    }
}
