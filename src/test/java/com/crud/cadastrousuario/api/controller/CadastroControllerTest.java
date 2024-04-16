package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.PersonCreateDTO;

import com.crud.cadastrousuario.domain.dto.PersonFilterDTO;
import com.crud.cadastrousuario.domain.dto.PersonResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.PersonMapper;

import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;

import com.crud.cadastrousuario.domain.model.Person;
import com.crud.cadastrousuario.domain.repository.PersonRepository;

import com.crud.cadastrousuario.domain.service.CrudPersonService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CadastroController.class)
@AutoConfigureMockMvc
class CadastroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudPersonService pessoaService;

    @MockBean
    private PersonMapper pessoaMapper;

    @MockBean
    private PersonRepository pessoaRepository;

    @InjectMocks
    private CadastroController cadastroController;


    @Autowired
    private ObjectMapper objectMapper;

    static final String PESSOA_API = "/cadastro/pessoa";

    private static PersonCreateDTO getPessoaCreateDTO() {

        PersonCreateDTO pessoaCreateDTO = PersonCreateDTO.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();
        return pessoaCreateDTO;

    }
    private static List<PersonResponseDTO> getPessoaResponseDTO(){

        List<PersonResponseDTO> listaPersonResponseDTO = new ArrayList<>();

        PersonResponseDTO personResponseDTO1 = PersonResponseDTO.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();

        PersonResponseDTO personResponseDTO2 = PersonResponseDTO.builder()
                .nome("Testano")
                .email("Testano@example.com")
                .telefone("10987654321")
                .build();

        listaPersonResponseDTO.add(personResponseDTO1);
        listaPersonResponseDTO.add(personResponseDTO2);

        return listaPersonResponseDTO;

    }

    private static Person getPessoa() {

        Person pessoa =  Person.builder()
                .nome("Teste")
                .email("teste@example.com")
                .telefone("12345678901")
                .build();
        return pessoa;

    }

    @Test
    public void testeDeveRetornar_UmaListaDePessoas_EverificarCadaCampo_MetodoGet() throws Exception {

        List<Person> listaPessoas = new ArrayList<>();
        List<PersonResponseDTO> listaPersonResponseDTO = getPessoaResponseDTO();


        when(pessoaService.searchPeople(any(Pageable.class), any(PersonFilterDTO.class))).thenReturn(listaPessoas);
        when(pessoaMapper.toDTO(listaPessoas)).thenReturn(listaPersonResponseDTO);

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
                .andExpect(content().json(objectMapper.writeValueAsString(listaPersonResponseDTO)));

    }

    @Test
    public void testDeveRetornar_UmaListaDePessoas_EverificarCadaCampo_SemParametros_MetodoGet() throws Exception {

        List<Person> listaPessoas = new ArrayList<>();
        List<PersonResponseDTO> listaPersonResponseDTO = getPessoaResponseDTO();


        when(pessoaService.searchPeople(any(Pageable.class), any(PersonFilterDTO.class))).thenReturn(listaPessoas);
        when(pessoaMapper.toDTO(listaPessoas)).thenReturn(listaPersonResponseDTO);

        mockMvc.perform(get(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@example.com"))
                .andExpect(jsonPath("$[0].telefone").value("12345678901"))
                .andExpect(jsonPath("$[1].nome").value("Testano"))
                .andExpect(jsonPath("$[1].email").value("Testano@example.com"))
                .andExpect(jsonPath("$[1].telefone").value("10987654321"))
                .andExpect(content().json(objectMapper.writeValueAsString(listaPersonResponseDTO)));

    }

    @Test
    public void testeVerificar_BuscaPorId_EVerificaCampos_MetodoGet() throws Exception {

        Long idTeste = 1L;

        Person pessoa = getPessoa();

        PersonResponseDTO personResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaService.searchPeopleByID(idTeste)).thenReturn(pessoa);
        when(pessoaMapper.toDTO(pessoa)).thenReturn(personResponseDTO);

        mockMvc.perform(get(PESSOA_API + "/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Teste"))
                .andExpect(jsonPath("email").value("teste@example.com"))
                .andExpect(jsonPath("telefone").value("12345678901"))
                .andExpect(content().json(objectMapper.writeValueAsString(personResponseDTO)));

    }

    @Test
    public void testeVerificar_ExceptionBuscaPorId_Inexistente_MetodoGet() throws Exception {

        Long idTeste = 100L;

       when(pessoaService.searchPeopleByID(idTeste)).thenThrow(new NotFoundException("Pessoa com o ID inexistente"));


        mockMvc.perform(get(PESSOA_API + "/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Pessoa com o ID inexistente"));

    }

    @Test
    public void testeVerificar_CadastrarPessoa_EVerificarCampos_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Person pessoa = new Person();
        PersonResponseDTO personResponseDTO = getPessoaResponseDTO().get(0);

        when(pessoaMapper.toEntity(any(PersonCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Person.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Person.class))).thenReturn(personResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);
        String pessoaResponseDTOJson = objectMapper.writeValueAsString(personResponseDTO);

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
    public void testeVerificar_BadRequest_PessoaComNomeMenorque3Caracteres_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setNome("TS");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void testeVerificar_BadRequest_PessoaComNomeMaiorque50Caracteres_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setNome("TesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTeste");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComTelefoneMenor_Que11Digitos_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setTelefone("1234567890");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComTelefoneMaior_Que13Digitos_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setTelefone("1234567890123456789");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComEmailEscritoIncorreto_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        String mudaEmail = "123123123131231";
        pessoaCreateDTO.setEmail(mudaEmail);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testeVerificar_PessoaComEmailNulo_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setEmail(null);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testedeveRetornarBadRequest_QuandoEmailJaCadastrado_MetodoPost() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Person pessoa = getPessoa();

        when(pessoaMapper.toEntity(any(PersonCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.save(any(Person.class)))
                .thenThrow(new BadRequestException("Email ja esta cadastrado"));

        String pessoaCreateDTOJson = new ObjectMapper().writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email ja esta cadastrado"));

    }



    @Test
    public void testeVerificar_AtualizarCadastro_ComEmailNulo_MetodoPut() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();
        pessoaCreateDTO.setEmail(null);
        Person pessoa = getPessoa();


        when(pessoaMapper.toEntity(any(PersonCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleByID(any(Long.class), any(Person.class)))
                .thenThrow(new BadRequestException("E-mail não pode ser vazio"));


        mockMvc.perform(put(PESSOA_API + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testeVerificar_AtualizaUmCadastro_MetodoPut() throws Exception {

        Long id = 1L;
        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Person pessoa = new Person();
        PersonResponseDTO personResponseDTO = getPessoaResponseDTO().get(0);
        when(pessoaMapper.toEntity(any(PersonCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleByID(eq(id), any(Person.class))).thenReturn(pessoa);
        when(pessoaMapper.toDTO(any(Person.class))).thenReturn(personResponseDTO);

        mockMvc.perform(put(PESSOA_API + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(personResponseDTO)));

    }

    @Test
    public void testedeveRetornarBadRequest_QuandoEmailJaCadastrado_MetodoPut() throws Exception {

        PersonCreateDTO pessoaCreateDTO = getPessoaCreateDTO();

        Person pessoa = getPessoa();

        when(pessoaMapper.toEntity(any(PersonCreateDTO.class))).thenReturn(pessoa);
        when(pessoaService.updatePeopleByID(anyLong(), any(Person.class)))
                .thenThrow(new BadRequestException("Email ja esta cadastrado"));

        String pessoaCreateDTOJson = new ObjectMapper().writeValueAsString(pessoaCreateDTO);


        mockMvc.perform(put(PESSOA_API + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value("Email ja esta cadastrado"));

    }


    @Test
    public void testeVerificar_DeletarUmCadastro_MetodoDelete() throws Exception {

        Long id = 1L;

        mockMvc.perform(delete(PESSOA_API + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Foi Removido com Sucesso"));

    }




}