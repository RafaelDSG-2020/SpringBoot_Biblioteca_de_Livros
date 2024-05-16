package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.LoanOfBooks;
import com.crud.cadastrousuario.domain.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanOfBooksDTO {



    private Long id;

    private Book bookId;

    private User usersId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRemoval;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateReturn;


    public LoanOfBooksDTO(LoanOfBooks loanOfBooks) {


        this.id = loanOfBooks.getId();
        this.bookId = loanOfBooks.getBookID();
        this.usersId = loanOfBooks.getUsersID();
        this.dateRemoval = loanOfBooks.getDateRemoval();
        this.dateReturn = loanOfBooks.getDateReturn();


    }
}
