package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.Stock;
import com.crud.cadastrousuario.domain.repository.AuthorRepositorySpec;
import com.crud.cadastrousuario.domain.repository.StockRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public List<StockDTO> findStock(Pageable pageable) {

        log.info("Executed the process of searching for author paged stock in the database, paeable={} ", pageable);
        Page<Stock> pageUser = stockRepository.findAll(

                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Stock> stocks = pageUser.getContent();

        return stocks.stream()
                .map(author -> new StockDTO())
                .collect(Collectors.toList());
    }
}
