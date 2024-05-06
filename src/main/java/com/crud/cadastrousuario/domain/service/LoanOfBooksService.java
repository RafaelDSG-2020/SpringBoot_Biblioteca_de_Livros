package com.crud.cadastrousuario.domain.service;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import com.crud.cadastrousuario.domain.exception.BadRequestException;
import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import com.crud.cadastrousuario.domain.model.User;
import com.crud.cadastrousuario.domain.repository.BookRepository;
import com.crud.cadastrousuario.domain.repository.LoanOfBooksRepository;
import com.crud.cadastrousuario.domain.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Log4j2
@Service
public class LoanOfBooksService {


    @Autowired
    private LoanOfBooksRepository loanOfBooksRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public LoanOfBooksDTO saveLoan(Long usersID, Long bookID ) {

        Book book = bookRepository.findById(bookID).orElseThrow(() -> new BadRequestException("Book not found"));
        User user = userRepository.findById(usersID).orElseThrow(() -> new BadRequestException("User not found"));


        LoanOfBooks loanOfBooks = new LoanOfBooks();
        loanOfBooks.setBookID(book);
        loanOfBooks.setUsersID(user);
        loanOfBooks = loanOfBooksRepository.save(loanOfBooks);
        return new LoanOfBooksDTO(loanOfBooks);

    }
}
