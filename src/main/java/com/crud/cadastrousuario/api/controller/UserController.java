package com.crud.cadastrousuario.api.controller;


import com.crud.cadastrousuario.domain.dto.UserDTO;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Log4j2
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private CrudUserService userService;





    @GetMapping
    public ResponseEntity<List<UserDTO>> findUserByParameters(@PageableDefault(size = 5) Pageable pageable, UserDTO filter) {

        log.info("Method: findUserByParameters searches for a set of paginated users 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<UserDTO> user = userService.findUser(pageable , filter);
        log.info("HTTP Method: GET Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", filter , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserByID(@PathVariable(value = "id") Long id){

        log.info("Method: findUserByID searches for just one user by their ID. HTTP Method: GET");
        long start = System.currentTimeMillis();
        UserDTO userSave = userService.findUserByID(id);
        log.info("HTTP Method: GET Endpoint: api/v1/users/{id}  payload = {} elapsedTime = {} ms", id , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }



    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDTO userCreateDTO){

        log.info("Method: saveUser creates a user in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        UserDTO userSave = userService.save(userCreateDTO);
        log.info("HTTP Method: POST Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", userSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateUser(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid UserDTO userCreateDTO) {

        log.info("Method: update User has the function of updating a user created in a table. HTTP Method: PUT");
        long start = System.currentTimeMillis();
        UserDTO userSave = userService.updateUserByID(id, userCreateDTO);
        log.info("HTTP Method: PUT Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", userSave , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(userSave);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserByID(@PathVariable(value = "id") Long id) {

        log.info("Method: deleteUserByID deletes a user by ID in the database. HTTP Method: DELETE ");
        long start = System.currentTimeMillis();
        userService.deleteUserByID(id);
        log.info("HTTP Method: DELETE Endpoint: api/v1/users elapsedTime = {} ms", (System.currentTimeMillis() - start));
        return ResponseEntity.noContent().build();
    }

}
