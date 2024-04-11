package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PessoaFilterDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.dto.PessoaCreateDTO;
import com.crud.cadastrousuario.domain.dto.PessoaResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.PessoaMapper;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.service.CrudPessoaService;
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
    private CrudPessoaService pessoaService;
    @Autowired
    private PessoaMapper pessoaMapper;


    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaResponseDTO>> buscaPessoas(@PageableDefault(size = 5) Pageable pageable, PessoaFilterDTO filter) {
     //   System.out.println(nameRef);
//        log.info();
        List<Pessoa> pessoas = pessoaService.searchPeople(pageable , filter);
        List<PessoaResponseDTO> pessoaResponseDTOS = pessoaMapper.toDTO(pessoas);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTOS);

    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> buscaPessoas(@PathVariable(value = "id") Long id) throws BadRequestException, NotFoundException {

        Pessoa pessoasave = pessoaService.searchPeopleofID(id);
        PessoaResponseDTO pessoaResponseDTO = pessoaMapper.toDTO(pessoasave);
        return  ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTO);

    }
    @PostMapping("/pessoa")
    public ResponseEntity<PessoaResponseDTO> saveRegistration(@RequestBody @Valid PessoaCreateDTO pessoaCreateDTO) throws NotFoundException, BadRequestException {

        Pessoa pessoa = pessoaMapper.toEntity(pessoaCreateDTO);
        Pessoa pessoaSalva = pessoaService.save(pessoa);
        PessoaResponseDTO pessoaResponseDTO = pessoaMapper.toDTO(pessoaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaResponseDTO);

    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<Object> atualizaUmCadastro(@PathVariable(value = "id") Long id,
                                                     @RequestBody @Valid  PessoaCreateDTO pessoaCreateDTO)
            throws NotFoundException, BadRequestException {
        Pessoa pessoa = pessoaMapper.toEntity(pessoaCreateDTO);
        Pessoa pessoaSalva = pessoaService.updatePeopleOFID(id, pessoa);
        PessoaResponseDTO pessoaResponseDTO = pessoaMapper.toDTO(pessoaSalva);
        return  ResponseEntity.status(HttpStatus.OK).body(pessoaResponseDTO);

    }


    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Object> deletaUmCadastro (@PathVariable(value = "id") Long id) throws NotFoundException, BadRequestException {

        pessoaService.deletePeopleOFID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Foi Removido com Sucesso");

    }

}
