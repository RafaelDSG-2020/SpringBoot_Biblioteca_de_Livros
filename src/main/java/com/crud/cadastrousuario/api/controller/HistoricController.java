package com.crud.cadastrousuario.api.controller;


import com.crud.cadastrousuario.domain.dto.HistoricDTO;
import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.service.HistoricService;
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
@RequestMapping("api/v1/histories")
@RestController
@RequiredArgsConstructor
public class HistoricController {


    @Autowired
    private HistoricService historicService;

    @GetMapping
    public ResponseEntity<List<HistoricDTO>> findHistoricsByParameters(@PageableDefault(size = 5) Pageable pageable, HistoricDTO filter) {

        log.info("Method: findHistoricsByParameters searches for a set of paginated historics 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<HistoricDTO> historic = historicService.findHistoric(pageable , filter);
        log.info("HTTP Method: GET Endpoint: api/v1/histories  payload = {} elapsedTime = {} ms", filter , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(historic);

    }


    @PostMapping("/{loanID}")
    public ResponseEntity<Object> saveHistoricLoan(@PathVariable Long loanID ){

        log.info("Method: saveLoan creates a loan in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        HistoricDTO historicSave = historicService.saveHistoricLoan(loanID );
        log.info("HTTP Method: POST Endpoint: api/v1/historic/{loanID}  payload = {} elapsedTime = {} ms", historicSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(historicSave);
    }
}
