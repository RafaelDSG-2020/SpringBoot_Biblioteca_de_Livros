package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;


import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.service.CrudAuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("api/v1/authors")
@RestController
@RequiredArgsConstructor
@Log4j2
public class AuthorController {

    @Autowired
    private CrudAuthorService authorService;



    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthorByParameters(@PageableDefault(size = 5) Pageable pageable, AuthorDTO filter ){

        log.info("Method: findAuthorByParameters searches for a set of paginated authors 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<AuthorDTO> authors = authorService.findAuthor(pageable , filter);
        log.info("HTTP Method: GET Endpoint: api/v1/authors  payload = {} elapsedTime = {} ms", filter , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(authors);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findAuthorByID(@PathVariable(value = "id") Long id){

        log.info("Method: findAuthorByID searches for just one author by their ID. HTTP Method: GET");
        long start = System.currentTimeMillis();
        AuthorDTO personSave = authorService.findAuthorByIDActive(id);

        log.info("HTTP Method: GET Endpoint: api/v1/authors/{id}  payload = {} elapsedTime = {} ms", id , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(personSave);

    }


    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO authorCreateDTO) {

        log.info("Method: saveAuthor creates a author in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        AuthorDTO authorSave = authorService.save(authorCreateDTO);

        log.info("HTTP Method: POST Endpoint: api/v1/authors  payload = {} elapsedTime = {} ms", authorSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorSave);
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Author> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        Author authorSave = authorService.addBookToAuthor(bookId, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorSave);
    }





    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid AuthorDTO authorCreateDTO) {

        log.info("Method: update Author has the function of updating a author created in a table. HTTP Method: PUT");
        long start = System.currentTimeMillis();
        AuthorDTO userSave = authorService.updateAuthorByID(id, authorCreateDTO);
        log.info("HTTP Method: PUT Endpoint: api/v1/authors  payload = {} elapsedTime = {} ms", userSave , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        log.info("Method: deleteUserByID deletes a author by ID in the database. HTTP Method: DELETE ");
        long start = System.currentTimeMillis();
        authorService.deleteAuthorByID(id);
        log.info("HTTP Method: DELETE Endpoint: api/v1/authors elapsedTime = {} ms", (System.currentTimeMillis() - start));
        return ResponseEntity.noContent().build();
    }


}
