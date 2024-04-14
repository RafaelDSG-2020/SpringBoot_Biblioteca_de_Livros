package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PessoaCreateDTO;
import com.crud.cadastrousuario.domain.dto.PessoaFilterDTO;
import com.crud.cadastrousuario.domain.dto.PessoaResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.PessoaMapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.model.Pessoa;
import com.crud.cadastrousuario.domain.repository.PessoaRepository;
import com.crud.cadastrousuario.domain.service.CrudPessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CadastroController.class)
@AutoConfigureMockMvc
class CadastroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudPessoaService pessoaService;

    @MockBean
    private PessoaMapper pessoaMapper;

    @MockBean
    private PessoaRepository pessoaRepository;


    @Autowired
    private ObjectMapper objectMapper;

    static final String PESSOA_API = "/cadastro/pessoa";

    private static PessoaCreateDTO getPessoaCreateDTO() {
        PessoaCreateDTO pessoaCreateDTO = PessoaCreateDTO.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();
        return pessoaCreateDTO;
    }
    private static List<PessoaResponseDTO> getPessoaResponseDTO(){
        List<PessoaResponseDTO> listaPessoaResponseDTO = new ArrayList<>();

        PessoaResponseDTO pessoaResponseDTO1 = PessoaResponseDTO.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();

        PessoaResponseDTO pessoaResponseDTO2 = PessoaResponseDTO.builder()
                .nome("Testano")
                .email("Testano@example.com")
                .telefone("10987654321")
                .build();

        listaPessoaResponseDTO.add(pessoaResponseDTO1);
        listaPessoaResponseDTO.add(pessoaResponseDTO2);

        return listaPessoaResponseDTO;
    }

    private static Pessoa getPessoa() {
        Pessoa pessoa =  Pessoa.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();
        return pessoa;
    }



    @Test
    @DisplayName("Deve Criar uma Lista de Pessoas e DTOS e Retornar isso")
    public void testBuscaPessoas() throws Exception {
        List<Pessoa> listaPessoas = new ArrayList<>();
        List<PessoaResponseDTO> listaPessoaResponseDTO = getPessoaResponseDTO();


        when(pessoaService.searchPeople(any(Pageable.class), any(PessoaFilterDTO.class))).thenReturn(listaPessoas);
        when(pessoaMapper.toDTO(listaPessoas)).thenReturn(listaPessoaResponseDTO);

        mockMvc.perform(get(PESSOA_API)
                        .param("nome", "Teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@example.com"))
                .andExpect(jsonPath("$[0].telefone").value("12345678901"))
                .andExpect(jsonPath("$[1].nome").value("Testano"))
                .andExpect(jsonPath("$[1].email").value("Testano@example.com"))
                .andExpect(jsonPath("$[1].telefone").value("10987654321"))
                .andExpect(content().json(objectMapper.writeValueAsString(listaPessoaResponseDTO)));

    }


    @Test
    @DisplayName("Deve Cria uma Pessoa e DTO e Retornar isso")
    public void testBuscaPessoaPorId() throws Exception {
        Long idTeste = 1L;

        Pessoa pessoa = getPessoa();

        PessoaResponseDTO pessoaResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaService.searchPeopleofID(idTeste)).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaResponseDTO);

        mockMvc.perform(get(PESSOA_API+"/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Teste"))
                .andExpect(jsonPath("email").value("teste@example.com"))
                .andExpect(jsonPath("telefone").value("12345678901"))
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaResponseDTO)));

    }



    @Test
    @DisplayName("Esse teste salva uma Pessoa e verifica a resposta")
    public void testSalvaPessoa() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Pessoa pessoa = new Pessoa();
        PessoaResponseDTO pessoaResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);
        String pessoaResponseDTOJson = objectMapper.writeValueAsString(pessoaResponseDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value("Teste"))
                .andExpect(jsonPath("email").value("teste@example.com"))
                .andExpect(jsonPath("telefone").value("12345678901"))
                .andExpect(content().json(pessoaResponseDTOJson));
    }




    @Test
    @DisplayName("Teste verifica se dar erro quando o email está incorreto")
    public void testSalvaPessoaComEmailIncorreto() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        String mudaEmail = "123123123131231"; // Email invalido
        pessoaCreateDTO.setEmail(mudaEmail);

        Pessoa pessoa = getPessoa();
        PessoaResponseDTO pessoaResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);


        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido
    }



    @Test
    @DisplayName("Teste verifica se dar erro quando o email está incorreto")
    public void testSalvaPessoaComEmailIncorretoPost() throws Exception {
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        //String mudaEmail = "123123123131231"; // Email invalido
        pessoaCreateDTO.setEmail(null);

        Pessoa pessoa = getPessoa();
        PessoaResponseDTO pessoaResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);


        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido
    }




    @Test /// Verificar se esse teste faz sentido
    @DisplayName("Verifica se o email está cadastrado")
    public void atualizaUmCadastro_EmailVazio() throws Exception {
        // Mockando o DTO de entrada com um e-mail já cadastrado
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setEmail(null);


        // Configurando o service para lançar BadRequestException quando tentar atualizar com um e-mail vazio
        when(pessoaService.updatePeopleOFID(any(Long.class), any(Pessoa.class)))
                .thenThrow(new BadRequestException("E-mail não pode ser vazio"));

        // Realizando a chamada PUT e verificando se a BadRequestException é lançada
        mockMvc.perform(put(PESSOA_API+"/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("Teste para atualizar ")
    public void testAtualizaUmCadastro() throws Exception {
        Long id = 1L;
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Pessoa pessoa = new Pessoa();
        PessoaResponseDTO pessoaResponseDTO = getPessoaResponseDTO().get(0);
        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleOFID(eq(id), any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Pessoa.class))).thenReturn(pessoaResponseDTO);

        mockMvc.perform(put(PESSOA_API+"/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pessoaResponseDTO)));
    }


    @Test
    public void testDeletaUmCadastro() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(PESSOA_API+"/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Foi Removido com Sucesso"));

    }

//    @Test
//    @DisplayName("Verifica se o sistema retorna BadRequest quando o email já está cadastrado")
//    public void deveRetornarBadRequest_QuandoEmailJaCadastrado() throws Exception {
//        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
//
//        doThrow(BadRequestException.class)
//                .when(pessoaService)
//                .isEmailAvailable(pessoaCreateDTO);
//
//        String pessoaCreateDTOJson = new ObjectMapper().writeValueAsString(pessoaCreateDTO);
//
//        mockMvc.perform(post(PESSOA_API)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(pessoaCreateDTOJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Email já está cadastrado."));
//    }

    @Test
    @DisplayName("Deve retornar BadRequest quando tentar atualizar com um email já cadastrado")
    public void deveRetornarBadRequest_QuandoEmailJaCadastrado() throws Exception {
        // Configura dados do DTO e a pessoa
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Pessoa pessoa = getPessoa();

        // Simula comportamentos do serviço e mapeamento
        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleOFID(anyLong(), any(Pessoa.class)))
                .thenThrow(new BadRequestException("Email ja esta cadastrado"));

        String pessoaCreateDTOJson = new ObjectMapper().writeValueAsString(pessoaCreateDTO);

        // Configuração e execução do teste MockMvc
        mockMvc.perform(put(PESSOA_API+"/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email ja esta cadastrado"));

    }





    @Test
    @DisplayName("Deve retornar BadRequest quando tentar atualizar com um email já cadastrado")
    public void deveRetornarBadRequest_QuandoEmailJaCadastradoPost() throws Exception {
        // Configura dados do DTO e a pessoa
        PessoaCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Pessoa pessoa = getPessoa();

        // Simula comportamentos do serviço e mapeamento
        when(pessoaMapper.toEntity(any(PessoaCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Pessoa.class)))
                .thenThrow(new BadRequestException("Email ja esta cadastrado"));

        String pessoaCreateDTOJson = new ObjectMapper().writeValueAsString(pessoaCreateDTO);

        // Configuração e execução do teste MockMvc
        mockMvc.perform(post(PESSOA_API, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email ja esta cadastrado"));

    }




}