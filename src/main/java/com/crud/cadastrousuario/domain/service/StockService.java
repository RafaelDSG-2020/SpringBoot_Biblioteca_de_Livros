package com.crud.cadastrousuario.domain.service;

import com.crud.cadastrousuario.domain.dto.StockDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.exception.NotFoundException;
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
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    BookRepository bookRepository;


    private final Integer STATUSFLAGACTIVE = 1;




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

        Book book = bookRepository.findById(bookID).orElseThrow(() ->
                new BadRequestException("Book not found"));

       stockRepository.findTopByBookId(bookID).ifPresent(stock -> {
            throw new BadRequestException( "Book already registered in stock");
        });


        Stock stock = new Stock(stockCreateDTO);
        stock.setBook(book);
        stockRepository.save(stock);

        return new StockDTO(stock);
    }

    public StockDTO updateStockByID(Long id , StockDTO stockCreateDTO) {

        log.info("Executed the process of updating stock by id in the database");
        Stock stockBook = stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock not found"));

        stockBook.setAmount(stockCreateDTO.getAmount());

        stockBook = stockRepository.save(stockBook);


        Book book = stockBook.getBook();
        book.setFlag(STATUSFLAGACTIVE);
        bookRepository.save(book);

        return new StockDTO(stockBook);
    }
}
