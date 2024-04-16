package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PersonFilterDTO;

import com.crud.cadastrousuario.domain.dto.mapper.PersonMapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.dto.PersonCreateDTO;
import com.crud.cadastrousuario.domain.dto.PersonResponseDTO;

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
@RequestMapping("/cadastro")
@RequiredArgsConstructor
@Log4j2
public class CadastroController {


    @Autowired
    private CrudPersonService pessoaService;
    @Autowired
    private PersonMapper pessoaMapper;


    @GetMapping("/pessoa")
    public ResponseEntity<List<PersonResponseDTO>> buscaPessoas(@PageableDefault(size = 5) Pageable pageable, PersonFilterDTO filter) {

        List<Person> pessoas = pessoaService.searchPeople(pageable , filter);
        List<PersonResponseDTO> personResponseDTOS = pessoaMapper.toDTO(pessoas);
        return ResponseEntity.status(HttpStatus.OK).body(personResponseDTOS);

    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<Object> buscaPessoas(@PathVariable(value = "id") Long id) throws NotFoundException {

        Person pessoasave = pessoaService.searchPeopleByID(id);
        PersonResponseDTO personResponseDTO = pessoaMapper.toDTO(pessoasave);
        return  ResponseEntity.status(HttpStatus.OK).body(personResponseDTO);

    }
    @PostMapping("/pessoa")
    public ResponseEntity<PersonResponseDTO> saveRegistration(@RequestBody @Valid PersonCreateDTO pessoaCreateDTO) throws NotFoundException, BadRequestException {

        Person pessoa = pessoaMapper.toEntity(pessoaCreateDTO);
        Person pessoaSalva = pessoaService.save(pessoa);
        PersonResponseDTO personResponseDTO = pessoaMapper.toDTO(pessoaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(personResponseDTO);

    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<Object> atualizaUmCadastro(@PathVariable(value = "id") Long id,
                                                     @RequestBody @Valid PersonCreateDTO pessoaCreateDTO)
            throws NotFoundException, BadRequestException {
        Person pessoa = pessoaMapper.toEntity(pessoaCreateDTO);
        Person pessoaSalva = pessoaService.updatePeopleByID(id, pessoa);
        PersonResponseDTO personResponseDTO = pessoaMapper.toDTO(pessoaSalva);
        return  ResponseEntity.status(HttpStatus.OK).body(personResponseDTO);

    }


    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Object> deletaUmCadastro (@PathVariable(value = "id") Long id) throws NotFoundException, BadRequestException {

        pessoaService.deletePeopleByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Foi Removido com Sucesso");

    }

}
