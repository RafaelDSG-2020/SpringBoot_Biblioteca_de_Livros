package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.mapper.Mapper;
import com.crud.cadastrousuario.domain.dto.UserDTO;

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
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private CrudUserService userService;
    @Autowired
    private Mapper userMapper;


    @GetMapping()
    public ResponseEntity<List<User>> findUserByParameters(@PageableDefault(size = 5) Pageable pageable, UserDTO filter) {

        List<User> user = userService.findUser(pageable , filter);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserByID(@PathVariable(value = "id") Long id){

        User userSave = userService.findUserByID(id);
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }



    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDTO userCreateDTO){

        User userSave = userService.save(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid UserDTO userCreateDTO) {

        User userSave = userService.updateUserByID(id, userCreateDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        userService.deletePeopleByID(id);
        return ResponseEntity.noContent().build();
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Foi Removido com Sucesso");
    }

}
