package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.dto.PersonDTO;


import com.crud.cadastrousuario.domain.model.Person;

import com.crud.cadastrousuario.domain.service.CrudPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Log4j2
public class RegisterController {


    @Autowired
    private CrudPersonService personService;
    @Autowired
    private Mapper personMapper;


    @GetMapping("/people")
    public ResponseEntity<List<PersonDTO>> searchPeople(@PageableDefault(size = 5) Pageable pageable, PersonDTO filter) {

        List<Person> people = personService.searchPeople(pageable , filter);
        List<PersonDTO> personResponseDTOS = personMapper.toDTO(people, PersonDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(personResponseDTOS);

    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Object> searchPeople(@PathVariable(value = "id") Long id) throws NotFoundException {

        Person personSave = personService.searchPeopleByID(id);
        PersonDTO personResponseDTO = personMapper.toDTO(personSave, PersonDTO.class);
        return  ResponseEntity.status(HttpStatus.OK).body(personResponseDTO);

    }
    @PostMapping("/people")
    public ResponseEntity<PersonDTO> saveRegistration(@RequestBody @Valid PersonDTO personCreateDTO) throws NotFoundException, BadRequestException {

      //  System.out.println(personCreateDTO);
        Person person = personMapper.toEntity(personCreateDTO,Person.class);
        Person personSave = personService.save(person);
        PersonDTO personResponseDTO = personMapper.toDTO(personSave, PersonDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponseDTO);

    }

    @PutMapping("/people/{id}")
    public ResponseEntity<Object> UpdateRegistration(@PathVariable(value = "id") Long id,
                                                     @RequestBody @Valid PersonDTO pessoaCreateDTO)
            throws NotFoundException, BadRequestException {
        Person person = personMapper.toEntity(pessoaCreateDTO , Person.class );
        Person personSave = personService.updatePeopleByID(id, person);
        PersonDTO personResponseDTO = personMapper.toDTO(personSave, PersonDTO.class);
        return  ResponseEntity.status(HttpStatus.OK).body(personResponseDTO);

    }


    @DeleteMapping("/people/{id}")
    public ResponseEntity<Object> deleteRegistration(@PathVariable(value = "id") Long id) throws NotFoundException, BadRequestException {

        personService.deletePeopleByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Foi Removido com Sucesso");

    }

}
