package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PessoaResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.PessoaMapper;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.service.CrudPessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CadastroController.class)
class CadastroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudPessoaService pessoaService;

    @MockBean
    private PessoaMapper pessoaMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void buscaPessoasTest() throws Exception {
        Pessoa pessoa = new Pessoa();
        PessoaResponseDTO dto = new PessoaResponseDTO();

        given(pessoaService.searchPeople(any(Pageable.class), any())).willReturn(Collections.singletonList(pessoa));
        given(pessoaMapper.toDTO(any(List.class))).willReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/cadastro/pessoas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

//    @Test
//    void buscaPessoasIDTest() throws Exception {
//        Pessoa pessoa = new Pessoa();
//        PessoaResponseDTO dto = new PessoaResponseDTO();
//
//        given(pessoaService.searchPeople(any(Pageable.class), any())).willReturn(Collections.singletonList(pessoa));
//        given(pessoaMapper.toDTO(any(List.class))).willReturn(Collections.singletonList(dto));
//
//        mockMvc.perform(get("/cadastro/pessoas/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }

}