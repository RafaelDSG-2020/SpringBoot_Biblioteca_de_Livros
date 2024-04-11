package com.crud.cadastrousuario.domain.service;

import static org.junit.jupiter.api.Assertions.*;


import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CrudPessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private CrudPessoaService pessoaService;

    @Test
    void shouldThrowBadRequestWhenEmailExists() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail("test@example.com");

        when(pessoaRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> pessoaService.isEmailAvailable(pessoa));

        verify(pessoaRepository).existsByEmail("test@example.com");
    }

    @Test
    void shouldPassWhenEmailNotExists() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail("test@example.com");

        when(pessoaRepository.existsByEmail(anyString())).thenReturn(false);

        assertDoesNotThrow(() -> pessoaService.isEmailAvailable(pessoa));

        verify(pessoaRepository).existsByEmail("test@example.com");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdNotExists() {
        Long id = 1L;
        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.isIdAvailable(id));

        verify(pessoaRepository).findById(id);
    }

    @Test
    void shouldPassWhenIdExists() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        assertDoesNotThrow(() -> pessoaService.isIdAvailable(id));

        verify(pessoaRepository).findById(id);
    }

}