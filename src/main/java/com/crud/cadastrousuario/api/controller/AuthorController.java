package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import com.crud.cadastrousuario.domain.dto.PersonDTO;
import com.crud.cadastrousuario.domain.dto.PersonResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.Person;
import com.crud.cadastrousuario.domain.service.CrudAuthorService;
import com.crud.cadastrousuario.domain.service.CrudPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("api/v1/authors")
@RestController()
public class AuthorController {

    @Autowired
    private CrudAuthorService authorService;

    @Autowired
    private Mapper authorMapper;


    @GetMapping()
    public ResponseEntity<List<Author>> findByAuthor(@PageableDefault(size = 5) Pageable pageable, AuthorDTO filter ){

        List<Author> authors = authorService.findByAuthor(pageable , filter);
        return ResponseEntity.status(HttpStatus.OK).body(authors);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByAuthorID(@PathVariable(value = "id") Long id){

        Author personSave = authorService.findAuthorByID(id);
        return  ResponseEntity.status(HttpStatus.OK).body(personSave);

    }

    @PostMapping()
    public ResponseEntity<Object> saveAuthor(@ResponseBody @Valid AuthorDTO authorCreateDTO){


        Author personSave = authorService.save(authorCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(personResponseDTO);
    }


}
