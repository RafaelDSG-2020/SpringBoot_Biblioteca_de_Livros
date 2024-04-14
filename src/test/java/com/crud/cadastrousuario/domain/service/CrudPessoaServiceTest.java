package com.crud.cadastrousuario.domain.service;

import static org.junit.jupiter.api.Assertions.*;


import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.repository.PessoaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CrudPessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private CrudPessoaService pessoaService;

    @Test
    @DisplayName("Teste verifica se o metodo retorna um email duplicado")
    void testbadRequestEmail() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail("test@example.com");

        when(pessoaRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> pessoaService.isEmailAvailable(pessoa));

    }

    @Test
    @DisplayName("Esse teste verifica se o ID existe")
    void testExistId() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        assertDoesNotThrow(() -> pessoaService.isIdAvailable(id));



    }

    @Test
    @DisplayName("Teste para verificar se o email não existe")
    void testPassWhen_EmailNotExists() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail("test@example.com");

        when(pessoaRepository.existsByEmail(anyString())).thenReturn(false);

        assertDoesNotThrow(() -> pessoaService.isEmailAvailable(pessoa));


    }

    @Test
    @DisplayName("Verifica quando não existe o ID ")
    void testNotFoundException_WhenIdNotExists() {
        Long id = 1L;
        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.isIdAvailable(id));


    }


}