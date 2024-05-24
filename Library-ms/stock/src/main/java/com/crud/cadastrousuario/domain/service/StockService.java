package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;


import com.crud.cadastrousuario.domain.model.Stock;

import com.crud.cadastrousuario.domain.repository.StockRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private RestTemplate restTemplate;

    //private String bookServiceUrl = "http://library-gateway:8082/book-ms/api/v1/books";
    private String bookServiceUrl = "http://localhost:8082/book-ms/api/v1/books";





    private final Integer STATUS_FLAG_ACTIVE = 1;


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

   // @Transactional
   public StockDTO save(Long bookID , StockDTO stockCreateDTO) {

       BookDTO book = restTemplate.getForObject(bookServiceUrl + "/" + bookID, BookDTO.class);
       Optional.ofNullable(book).orElseThrow(() -> new BadRequestException("Book not found"));



       stockRepository.findTopByBookId(bookID).ifPresent(stock -> {
           throw new BadRequestException( "Book already registered in stock");
       });


       Stock stock = new Stock(stockCreateDTO);
       stock.setBook(book.getId());
       stockRepository.save(stock);

       return new StockDTO(stock);
   }

    public StockDTO updateStockByID(Long id , StockDTO stockCreateDTO) {


        log.info("Executed the process of updating stock by id in the database");
        Stock stockBook = stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock not found"));

        stockBook.setAmount(stockCreateDTO.getAmount());

        stockBook = stockRepository.save(stockBook);



        BookDTO book = restTemplate.getForObject(bookServiceUrl + "/" + stockBook.getBook() , BookDTO.class);
        book.setFlag(STATUS_FLAG_ACTIVE);
        restTemplate.put(bookServiceUrl + "/" + book.getId() , book);


        return new StockDTO(stockBook);
    }

    public StockDTO findBookID(Long bookID) {


        Optional<Stock> opt =  stockRepository.findTopByBookId(bookID);
        return new StockDTO(opt.get());
    }
}
