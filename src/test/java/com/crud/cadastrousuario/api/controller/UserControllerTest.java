package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.UserDTO;

import com.crud.cadastrousuario.domain.dto.UserResponseDTO;
import com.crud.cadastrousuario.domain.dto.mapper.Mapper;

import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;

import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.UserRepository;

import com.crud.cadastrousuario.domain.service.CrudUserService;

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
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudUserService userService;

    @MockBean
    private Mapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;


    @Autowired
    private ObjectMapper objectMapper;

    static final String PESSOA_API = "/api/v1/register/user";

    private static UserDTO getUserCreateDTO() {

        UserDTO userCreateDTO = UserDTO.builder()
                .name("Teste")
                .email("teste@example.com")
                .phone("12345678901")
                .build();
        return userCreateDTO;

    }
    private static List<UserResponseDTO> getUserResponseDTO(){

        List<UserResponseDTO> listaUserResponseDTO = new ArrayList<>();

        UserResponseDTO userResponseDTO1 = UserResponseDTO.builder()
                .name("Teste")
                .email("teste@example.com")
                .phone("12345678901")
                .build();

        UserResponseDTO userResponseDTO2 = UserResponseDTO.builder()
                .name("Testano")
                .email("Testano@example.com")
                .phone("10987654321")
                .build();

        listaUserResponseDTO.add(userResponseDTO1);
        listaUserResponseDTO.add(userResponseDTO2);

        return listaUserResponseDTO;

    }

    private static User getUser() {

        User pessoa =  User.builder()
                .name("Teste")
                .email("teste@example.com")
                .phone("12345678901")
                .build();
        return pessoa;

    }

    @Test
    public void testeDeveRetornar_UmaListaDePessoas_EverificarCadaCampo_MetodoGet() throws Exception {

        List<User> listaUser = new ArrayList<>();
        List<UserResponseDTO> listaUserResponseDTO = getUserResponseDTO();


        when(userService.findUser(any(Pageable.class), any(UserDTO.class))).thenReturn(listaUser);
        when(userMapper.toDTO(any(List.class), eq(UserResponseDTO.class))).thenReturn(listaUserResponseDTO);

        mockMvc.perform(get(PESSOA_API)
                        .param("nome", "Teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@example.com"))
                .andExpect(jsonPath("$[0].phone").value("12345678901"))
                .andExpect(jsonPath("$[1].name").value("Testano"))
                .andExpect(jsonPath("$[1].email").value("Testano@example.com"))
                .andExpect(jsonPath("$[1].phone").value("10987654321"))
                .andExpect(content().json(objectMapper.writeValueAsString(listaUserResponseDTO)));

    }

    @Test
    public void testDeveRetornar_UmaListaDePessoas_EverificarCadaCampo_SemParametros_MetodoGet() throws Exception {

        List<User> listaPessoas = new ArrayList<>();
        List<UserResponseDTO> listaUserResponseDTO = getUserResponseDTO();


        when(userService.findUser(any(Pageable.class), any(UserDTO.class))).thenReturn(listaPessoas);
        when(userMapper.toDTO(any(List.class), eq(UserResponseDTO.class))).thenReturn(listaUserResponseDTO);



        mockMvc.perform(get(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Teste"))
                .andExpect(jsonPath("$[0].email").value("teste@example.com"))
                .andExpect(jsonPath("$[0].phone").value("12345678901"))
                .andExpect(jsonPath("$[1].name").value("Testano"))
                .andExpect(jsonPath("$[1].email").value("Testano@example.com"))
                .andExpect(jsonPath("$[1].phone").value("10987654321"))
                .andExpect(content().json(objectMapper.writeValueAsString(listaUserResponseDTO)));

    }

    @Test
    public void testeVerificar_BuscaPorId_EVerificaCampos_MetodoGet() throws Exception {

        Long idTeste = 1L;

        User pessoa = getUser();

        UserResponseDTO userResponseDTO = getUserResponseDTO().get(0);

        when(userService.findUserByID(idTeste)).thenReturn(pessoa);
        when(userMapper.toDTO(pessoa, UserResponseDTO.class)).thenReturn(userResponseDTO);


        mockMvc.perform(get(PESSOA_API + "/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Teste"))
                .andExpect(jsonPath("email").value("teste@example.com"))
                .andExpect(jsonPath("phone").value("12345678901"))
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDTO)));

    }

    @Test
    public void testeVerificar_ExceptionBuscaPorId_Inexistente_MetodoGet() throws Exception {

        Long idTeste = 100L;

       when(userService.findUserByID(idTeste)).thenThrow(new NotFoundException("Pessoa com o ID inexistente"));


        mockMvc.perform(get(PESSOA_API + "/{id}", idTeste)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Pessoa com o ID inexistente"));

    }

    @Test
    public void testeVerificar_CadastrarPessoa_EVerificarCampos_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();

        User pessoa = new User();
        UserResponseDTO userResponseDTO = getUserResponseDTO().get(0);

        when(userMapper.toEntity(any(UserDTO.class), eq(User.class))).thenReturn(pessoa);
        when(userService.save(any(User.class))).thenReturn(pessoa);
        when(userMapper.toDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);
        String pessoaResponseDTOJson = objectMapper.writeValueAsString(userResponseDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("Teste"))
                .andExpect(jsonPath("email").value("teste@example.com"))
                .andExpect(jsonPath("phone").value("12345678901"))
                .andExpect(content().json(pessoaResponseDTOJson));

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComNomeMenorque3Caracteres_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setName("TS");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void testeVerificar_BadRequest_PessoaComNomeMaiorque50Caracteres_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setName("TesteTesteTesteTesteTesteTesteTesteTesteTesteTesteTeste");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComTelefoneMenor_Que11Digitos_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setPhone("1234567890");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComTelefoneMaior_Que13Digitos_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setPhone("1234567890123456789");

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest()); // Verificação do email invalido

    }

    @Test
    public void testeVerificar_BadRequest_PessoaComEmailEscritoIncorreto_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();
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

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setEmail(null);

        String pessoaCreateDTOJson = objectMapper.writeValueAsString(pessoaCreateDTO);

        mockMvc.perform(post(PESSOA_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaCreateDTOJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testedeveRetornarBadRequest_QuandoEmailJaCadastrado_MetodoPost() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();

        User pessoa = getUser();

        when(userMapper.toEntity(any(UserDTO.class), eq(User.class))).thenReturn(pessoa);
        when(userService.save(any(User.class)))
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

        UserDTO pessoaCreateDTO = getUserCreateDTO();
        pessoaCreateDTO.setEmail(null);
        User pessoa = getUser();


        when(userMapper.toEntity(any(UserDTO.class), eq(User.class))).thenReturn(pessoa);
        when(userService.updateUserByID(any(Long.class), any(User.class)))
                .thenThrow(new BadRequestException("E-mail não pode ser vazio"));


        mockMvc.perform(put(PESSOA_API + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testeVerificar_AtualizaUmCadastro_MetodoPut() throws Exception {

        Long id = 1L;
        UserDTO pessoaCreateDTO = getUserCreateDTO();

        User pessoa = new User();
        UserResponseDTO userResponseDTO = getUserResponseDTO().get(0);
        when(userMapper.toEntity(any(UserDTO.class) , eq(User.class))).thenReturn(pessoa);
        when(userService.updateUserByID(eq(id), any(User.class))).thenReturn(pessoa);
        when(userMapper.toDTO(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(put(PESSOA_API + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseDTO)));

    }

    @Test
    public void testedeveRetornarBadRequest_QuandoEmailJaCadastrado_MetodoPut() throws Exception {

        UserDTO pessoaCreateDTO = getUserCreateDTO();

        User pessoa = getUser();

        when(userMapper.toEntity(any(UserDTO.class) , eq(User.class))).thenReturn(pessoa);
        when(userService.updateUserByID(anyLong(), any(User.class)))
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