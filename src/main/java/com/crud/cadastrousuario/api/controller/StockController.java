package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.model.Stock;
import com.crud.cadastrousuario.domain.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
@RestController
public class StockController {


    @Autowired
    StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findStockByParameters(@PageableDefault(size = 5) Pageable pageable) {

        log.info("Method: findUserByParameters searches for a set of paginated users 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<StockDTO> user = stockService.findStock(pageable );
        log.info("HTTP Method: GET Endpoint: api/v1/users   elapsedTime = {} ms",  (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
