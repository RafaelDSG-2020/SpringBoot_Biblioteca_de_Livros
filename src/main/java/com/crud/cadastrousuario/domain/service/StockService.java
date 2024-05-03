package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.Stock;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.BookRepository;
import com.crud.cadastrousuario.domain.repository.StockRepository;
import com.crud.cadastrousuario.domain.repository.UserRepositorySpec;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    BookRepository bookRepository;




    public List<StockDTO> findBooksInStock(Pageable pageable) {

        log.info("Executed the process of searching for stock paged books in the database, paeable={} ", pageable);
        Page<Stock> pageStock = stockRepository.findAll(
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<Stock> stocks = pageStock.getContent();

        return stocks.stream()
                .map(stock -> new StockDTO(stock))
                .collect(Collectors.toList());
    }

    @Transactional
    public StockDTO save(Long bookID , StockDTO stockCreateDTO) {

        Book book = bookRepository.findById(bookID).orElseThrow(() -> new RuntimeException("Book not found"));

        Stock stock = new Stock(stockCreateDTO);
        stock.setBook(book);
        stockRepository.save(stock);

        return new StockDTO(stock);
    }

}
