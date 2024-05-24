package com.crud.cadastrousuario.api.controller;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.service.LoanOfBooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/v1/loans")
@RequiredArgsConstructor
public class LoanOfBooksController {


    @Autowired
    private LoanOfBooksService loanOfBooksService;

    @GetMapping
    public ResponseEntity<List<LoanOfBooksDTO>> findLoansByParameters(@PageableDefault(size = 5) Pageable pageable, LoanOfBooksDTO filter) {

        log.info("Method: findLoansByParameters searches for a set of paginated users 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<LoanOfBooksDTO> loan = loanOfBooksService.findLoans(pageable , filter);
        log.info("HTTP Method: GET Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", filter , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(loan);

    }

    @PostMapping("/{usersID}/loans/{bookID}")
    public ResponseEntity<Object> saveLoan(@PathVariable Long usersID , @PathVariable Long bookID ){

        log.info("Method: saveLoan creates a loan in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        LoanOfBooksDTO loanOfBooksSave = loanOfBooksService.saveLoan(usersID , bookID );
        log.info("HTTP Method: POST Endpoint: api/v1/loan/{usersID}/loans/{bookID}  payload = {} elapsedTime = {} ms", loanOfBooksSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(loanOfBooksSave);
    }


}
