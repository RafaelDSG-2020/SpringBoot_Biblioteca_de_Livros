package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;


import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Author;

import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.service.CrudAuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class AuthorController {

    @Autowired
    private CrudAuthorService authorService;

    @Autowired
    private Mapper authorMapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);



    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAuthorByParameters(@PageableDefault(size = 5) Pageable pageable, AuthorDTO filter ){

        LOGGER.info("Method: findAuthorByParameters searches for a set of paginated authors 5 by 5. HTTP Method: GET");

        List<AuthorDTO> authors = authorService.findAuthor(pageable , filter);
        return ResponseEntity.status(HttpStatus.OK).body(authors);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findAuthorByID(@PathVariable(value = "id") Long id){

        LOGGER.info("Method: findAuthorByID searches for just one author by their ID. HTTP Method: GET");

        AuthorDTO personSave = authorService.findAuthorByID(id);
        return  ResponseEntity.status(HttpStatus.OK).body(personSave);

    }


    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO authorCreateDTO) {

        LOGGER.info("Method: saveAuthor creates a author in the database. HTTP Method: POST");

        AuthorDTO personSave = authorService.save(authorCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid AuthorDTO authorCreateDTO) {

        LOGGER.info("Method: update Author has the function of updating a author created in a table. HTTP Method: PUT");

        AuthorDTO userSave = authorService.updateAuthorByID(id, authorCreateDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        LOGGER.info("Method: deleteUserByID deletes a author by ID in the database. HTTP Method: DELETE ");
        authorService.deleteAuthorByID(id);
        return ResponseEntity.noContent().build();
    }


}
