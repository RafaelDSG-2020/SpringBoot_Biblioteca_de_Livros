package com.crud.cadastrousuario.api.controller;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.model.Stock;
import com.crud.cadastrousuario.domain.service.StockService;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
@RestController
public class StockController {


    @Autowired
    StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDTO>> findBooksInStock(@PageableDefault(size = 5) Pageable pageable){

        log.info("Method: findBooksInStock searches for a set of paginated stock 5 by 5. HTTP Method: GET");
        long start = System.currentTimeMillis();
        List<StockDTO> stocks = stockService.findBooksInStock(pageable);
        log.info("HTTP Method: GET Endpoint: api/v1/stock  elapsedTime = {} ms" , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.OK).body(stocks);

    }




    @PostMapping("/{bookID}")
    public ResponseEntity<Object> saveUser(@PathVariable Long bookID , @RequestBody @Valid StockDTO stockCreateDTO){

        log.info("Method: saveUser creates a user in the database. HTTP Method: POST");
        long start = System.currentTimeMillis();
        StockDTO stockSave = stockService.save(bookID , stockCreateDTO);
        log.info("HTTP Method: POST Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", stockSave , (System.currentTimeMillis() - start));
        return ResponseEntity.status(HttpStatus.CREATED).body(stockSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> UpdateStock(@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid StockDTO stockCreateDTO) {

        log.info("Method: update User has the function of updating a user created in a table. HTTP Method: PUT");
        long start = System.currentTimeMillis();
        StockDTO stockSave = stockService.updateStockByID(id , stockCreateDTO);
        log.info("HTTP Method: PUT Endpoint: api/v1/users  payload = {} elapsedTime = {} ms", stockSave , (System.currentTimeMillis() - start));
        return  ResponseEntity.status(HttpStatus.OK).body(stockSave);

    }
}
