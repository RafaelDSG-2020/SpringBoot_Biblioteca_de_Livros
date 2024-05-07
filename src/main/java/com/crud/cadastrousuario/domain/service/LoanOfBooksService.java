package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.dto.UserDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import com.crud.cadastrousuario.domain.model.Stock;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class LoanOfBooksService {


    @Autowired
    private LoanOfBooksRepository loanOfBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    private final Integer STATUSFLAGACTIVE = 1;

    private final Integer STATUSFLAGINACTIVE = 0;

    private final Integer DECREASEVALUE = 1;


    @Transactional
    public LoanOfBooksDTO saveLoan(Long usersID, Long bookID ) {

        Book book = findEntityById(bookRepository, bookID, "Book not found");
        User user = findEntityById(userRepository, usersID, "User not found");
        Stock stock = findEntityById(stockRepository, bookID, "Stock not found");

//        Book book = bookRepository.findById(bookID).orElseThrow(() -> new BadRequestException("Book not found"));
//        User user = userRepository.findById(usersID).orElseThrow(() -> new BadRequestException("User not found"));
//        Stock stock = stockRepository.findByBook_Id(bookID).orElseThrow(() -> new BadRequestException("Stock not found"));

        checkAndDecrementStock(stock);
//        if(stock.getAmount().equals(0)){
//            throw new BadRequestException("The stock of book is empty");
//        }
//        stock.setAmount((stock.getAmount()-1));
        LoanOfBooks loanOfBooks = new LoanOfBooks();
        loanOfBooks.setBookID(book);
        loanOfBooks.setUsersID(user);
        loanOfBooks = loanOfBooksRepository.save(loanOfBooks);
        return new LoanOfBooksDTO(loanOfBooks);

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


    private <T> T findEntityById(JpaRepository<T, Long> repository, Long id, String messageError) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException(messageError));
    }

    private void checkAndDecrementStock(Stock stock) {
        if (stock.getAmount().equals(0) && bookRepository.existsByFlag(STATUSFLAGACTIVE)) {
            throw new BadRequestException("The stock of book is empty");
        }

        stock.setAmount(stock.getAmount() - DECREASEVALUE);

        if (stock.getAmount().equals(STATUSFLAGINACTIVE)) {
            Book book = stock.getBook();
            book.setFlag(STATUSFLAGINACTIVE);
            bookRepository.save(book);
        }
    }

}
