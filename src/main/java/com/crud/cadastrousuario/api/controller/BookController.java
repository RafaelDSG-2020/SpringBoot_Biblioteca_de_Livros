package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.service.CrudBookService;
import jakarta.validation.Valid;
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
public class BookController {

    @Autowired
    private CrudBookService bookService;
    @Autowired
    private Mapper bookMapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);


    @GetMapping()
    public ResponseEntity<List<Book>> findBookByParameters(@PageableDefault(size = 5) Pageable pageable, BookDTO filter) {

        LOGGER.info("Method: findUserByParameters searches for a set of paginated books 5 by 5. HTTP Method: GET");
        List<Book> books = bookService.findUser(pageable , filter);
        return ResponseEntity.status(HttpStatus.OK).body(books);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findBookByID(@PathVariable(value = "id") Long id){

        LOGGER.info("Method: findUserByID searches for just one book by their ID. HTTP Method: GET");
        Book bookSave = bookService.findBookByID(id);
        return  ResponseEntity.status(HttpStatus.OK).body(bookSave);

    }



    @PostMapping()
    public ResponseEntity<Object> saveBook(@RequestBody @Valid BookDTO bookCreateDTO){

        LOGGER.info("Method: saveUser creates a book in the database. HTTP Method: POST");
        Book bookSave = bookService.save(bookCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid BookDTO bookCreateDTO) {

        LOGGER.info("Method: update User has the function of updating a book created in a table. HTTP Method: PUT");

        Book bookSave = bookService.updateBookByID(id, bookCreateDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(bookSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        LOGGER.info("Method: deleteUserByID deletes a book by ID in the database. HTTP Method: DELETE ");
        bookService.deleteBookByID(id);
        return ResponseEntity.noContent().build();
    }
}
