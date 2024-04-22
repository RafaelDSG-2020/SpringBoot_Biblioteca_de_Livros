package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.dto.UserDTO;

import com.crud.cadastrousuario.domain.model.User;

import com.crud.cadastrousuario.domain.service.CrudUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private CrudUserService userService;
    @Autowired
    private Mapper userMapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @GetMapping()
    public ResponseEntity<List<User>> findUserByParameters(@PageableDefault(size = 5) Pageable pageable, UserDTO filter) {

        LOGGER.info("START OF THE GET METHOD By PARAMETERS");
        List<User> user = userService.findUser(pageable , filter);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserByID(@PathVariable(value = "id") Long id){

        LOGGER.info("START OF THE GET METHOD By Id");
        User userSave = userService.findUserByID(id);
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }



    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDTO userCreateDTO){

        LOGGER.info("START OF THE POST METHOD SAVE USER");

        User userSave = userService.save(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid UserDTO userCreateDTO) {

        LOGGER.info("START OF THE PUT METHOD UPDATE PARAMETERS");

        User userSave = userService.updateUserByID(id, userCreateDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        LOGGER.info("START OF THE DELETE METHOD ");
        userService.deletePeopleByID(id);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Foi Removido com Sucesso");
    }

}
