package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.model.*;
import com.crud.cadastrousuario.domain.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class LoanOfBooksService {


    @Autowired
    private LoanOfBooksRepository loanOfBooksRepository;

    @Autowired
    RestTemplate restTemplate;

    private String bookServiceUrl = "http://library-gateway:8082/book-ms/api/v1/books";
    private String userServiceUrl = "http://library-gateway:8082/user-ms/api/v1/users";
    private String stockServiceUrl = "http://library-gateway:8082/stock-ms/api/v1/stock";

    private final Integer STATUS_FLAG_ACTIVE = 1;

    private final Integer STATUS_FLAG_INACTIVE = 0;

    private final Integer DECREASE_VALUE = 1;

    private final Integer THRESSHOLD_VALUE = 0;



 //   @Transactional
    public LoanOfBooksDTO saveLoan(Long usersID, Long bookID ) {

        Book book = findEntityById(bookServiceUrl, bookID, Book.class ,  "Book not found");
        User user = findEntityById(userServiceUrl, usersID, User.class , "User not found");
        Stock stock = findEntityById(stockServiceUrl, bookID, Stock.class , "Stock not found");

        checkAndDecrementStock(stock , book.getId());

        LoanOfBooks loanOfBooks = new LoanOfBooks();
        loanOfBooks.setBookID(book);
        loanOfBooks.setUsersID(user);
        loanOfBooks.setStatus(Status.EMPRESTADO);
        loanOfBooks = loanOfBooksRepository.save(loanOfBooks);
        return new LoanOfBooksDTO(loanOfBooks);

    }


    private <T> T findEntityById(String url , Long id, Class<T> tClass ,String messageError) {

        return Optional.ofNullable(restTemplate.getForObject(url + "/" + id , tClass))
                .orElseThrow(() -> new BadRequestException(messageError));

    }



    public List<LoanOfBooksDTO> findLoans(Pageable pageable, LoanOfBooksDTO filter) {

        log.info("Executed the process of searching for user paged user in the database, paeable={} ", pageable);
        Page<LoanOfBooks> pageLoan = loanOfBooksRepository.findAll(
                LoanOfBooksSpec.filter(filter),
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()));

        List<LoanOfBooks> loans = pageLoan.getContent();

        return loans.stream()
                .map(loan -> new LoanOfBooksDTO(loan))
                .collect(Collectors.toList());
    }




    private void checkAndDecrementStock(Stock stock , Long id) {

        Book book = restTemplate.getForObject(bookServiceUrl + "/" + id , Book.class);
        if (stock.getAmount().equals(THRESSHOLD_VALUE) && book.getFlag().equals(STATUS_FLAG_ACTIVE)) {
            throw new BadRequestException("The stock of book is empty");
        }

        stock.setAmount(stock.getAmount() - DECREASE_VALUE);
        restTemplate.put(stockServiceUrl + "/" + stock.getId() , stock);

        if (stock.getAmount().equals(THRESSHOLD_VALUE)) {
            Book bookStock = stock.getBook();
            bookStock.setFlag(STATUS_FLAG_INACTIVE);
            restTemplate.put(bookServiceUrl + "/" + bookStock.getId() , bookStock);
           // bookRepository.save(book);
        }
    }

}
