package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PessoaCreateDTO;
import com.crud.cadastrousuario.domain.dto.PessoaFilterDTO;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
    public void testBuscaPessoas() throws Exception {
        List<Pessoa> listaPessoas = new ArrayList<>();
        List<PessoaResponseDTO> listaPessoaResponseDTO = new ArrayList<>();

        PessoaResponseDTO pessoaResponseDTO1 = new PessoaResponseDTO();
        pessoaResponseDTO1.setNome("Teste");
        pessoaResponseDTO1.setEmail("Teste@example.com");
        pessoaResponseDTO1.setTelefone("12345678901");

        PessoaResponseDTO pessoaResponseDTO2 = new PessoaResponseDTO();
        pessoaResponseDTO2.setNome("Testano");
        pessoaResponseDTO2.setEmail("Testano@example.com");
        pessoaResponseDTO2.setTelefone("10987654321");

        listaPessoaResponseDTO.add(pessoaResponseDTO1);
        listaPessoaResponseDTO.add(pessoaResponseDTO2);


        when(pessoaService.searchPeople(any(Pageable.class), any(PessoaFilterDTO.class))).thenReturn(listaPessoas);
        when(pessoaMapper.toDTO(listaPessoas)).thenReturn(listaPessoaResponseDTO);

        mockMvc.perform(get("/cadastro/pessoas")
                        .param("nome", "Teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listaPessoaResponseDTO)));

    }


    @Test
    public void testBuscaPessoaPorId() throws Exception {
        Long idTeste = 1L;
        Pessoa pessoa = new Pessoa();

        pessoa.setId(idTeste);
        pessoa.setNome("Teste");
        pessoa.setEmail("teste@example.com");
        pessoa.setTelefone("12345678901");

        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();

        pessoaResponseDTO.setNome(pessoa.getNome());
        pessoaResponseDTO.setEmail(pessoa.getEmail());
        pessoaResponseDTO.setTelefone(pessoa.getTelefone());

        when(pessoaService.searchPeopleofID(idTeste)).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaResponseDTO);

        mockMvc.perform(get("/cadastro/pessoas/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaResponseDTO)));

    }

    @Test
    public void testSalvaPessoa() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = PessoaCreateDTO.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();

        Pessoa pessoa = new Pessoa();
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setNome(pessoaCreateDTO.getNome());
        pessoaResponseDTO.setEmail(pessoaCreateDTO.getEmail());
        pessoaResponseDTO.setTelefone(pessoaCreateDTO.getTelefone());

        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);
        String pessoaResponseDTOJson = objectMapper.writeValueAsString(pessoaResponseDTO);

        mockMvc.perform(post("/cadastro/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(pessoaResponseDTOJson));
    }


    @Test
    public void testAtualizaUmCadastro() throws Exception {
        Long id = 1L;
        PessoaCreateDTO pessoaCreateDTO = PessoaCreateDTO.builder()
                .nome("Fulano de Tal")
                .email("fulano@example.com")
                .telefone("12345678901")
                .build();

        Pessoa pessoa = new Pessoa();
        PessoaResponseDTO pessoaResponseDTO = new PessoaResponseDTO();
        pessoaResponseDTO.setNome(pessoaCreateDTO.getNome());
        pessoaResponseDTO.setEmail(pessoaCreateDTO.getEmail());
        pessoaResponseDTO.setTelefone(pessoaCreateDTO.getTelefone());

        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleOFID(eq(id), any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        mockMvc.perform(put("/cadastro/pessoa/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaResponseDTO)));
    }


    @Test
    public void testDeletaUmCadastro() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/cadastro/pessoa/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Foi Removido com Sucesso"));

    }

}