package com.crud.cadastrousuario.api.controller;


import com.crud.cadastrousuario.domain.dto.HistoricDTO;
import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.service.HistoricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("api/v1/histories")
@RestController
@RequiredArgsConstructor
public class HistoricController {


    @Autowired
    private HistoricService historicService;

    @PostMapping("/{loanID}")
    public ResponseEntity<Object> saveHistoricLoan(@PathVariable Long loanID ){

        log.info("Method: saveLoan creates a loan in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        HistoricDTO historicSave = historicService.saveHistoricLoan(loanID );
        log.info("HTTP Method: POST Endpoint: api/v1/historic/{loanID}  payload = {} elapsedTime = {} ms", historicSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(historicSave);
    }
}
