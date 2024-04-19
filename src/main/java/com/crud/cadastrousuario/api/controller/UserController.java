package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.dto.UserResponseDTO;

import com.crud.cadastrousuario.domain.model.User;

import com.crud.cadastrousuario.domain.service.CrudUserService;
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
@RequestMapping("api/v1/register")
@RequiredArgsConstructor
@Log4j2
public class UserController {


    @Autowired
    private CrudUserService userService;
    @Autowired
    private Mapper userMapper;


    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDTO>> findUser(@PageableDefault(size = 5) Pageable pageable, UserDTO filter) {

        List<User> user = userService.findUser(pageable , filter);
        List<UserResponseDTO> userResponseDTOS = userMapper.toDTO(user, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOS);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> findUser(@PathVariable(value = "id") Long id) throws NotFoundException {

        User userSave = userService.findUserByID(id);
        UserResponseDTO userResponseDTO = userMapper.toDTO(userSave, UserResponseDTO.class);
        return  ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }


    @PostMapping("/user")
    public ResponseEntity<Object> saveRegistration(@RequestBody @Valid UserDTO userCreateDTO) throws NotFoundException, BadRequestException {


        User user = userMapper.toEntity(userCreateDTO, User.class);
        User userSave = userService.save(user);
        UserResponseDTO userResponseDTO = userMapper.toDTO(userSave, UserResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);

    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Object> UpdateRegistration(@PathVariable(value = "id") Long id,
                                                     @RequestBody @Valid UserDTO userCreateDTO)
            throws NotFoundException, BadRequestException {
        User user = userMapper.toEntity(userCreateDTO , User.class );
        User userSave = userService.updateUserByID(id, user);
        UserResponseDTO userResponseDTO = userMapper.toDTO(userSave, UserResponseDTO.class);
        return  ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);

    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteRegistration(@PathVariable(value = "id") Long id) throws NotFoundException, BadRequestException {

        userService.deletePeopleByID(id);
        return ResponseEntity.status(HttpStatus.OK).body("Foi Removido com Sucesso");

    }

}
