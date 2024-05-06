package com.crud.cadastrousuario.api.controller;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.service.LoanOfBooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("api/v1/loan")
@RequiredArgsConstructor
public class LoanOfBooksController {


    @Autowired
    private LoanOfBooksService loanOfBooksService;

    @PostMapping("/{usersID}/loans/{bookID}")
    public ResponseEntity<Object> saveLoan(@PathVariable Long usersID , @PathVariable Long bookID ){

        LoanOfBooksDTO loanOfBooksSave = loanOfBooksService.saveLoan(usersID , bookID );
        return ResponseEntity.status(HttpStatus.CREATED).body(loanOfBooksSave);
    }


}
