package com.crud.cadastrousuario.domain.dto;



import com.crud.cadastrousuario.domain.model.LoanOfBooks;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanOfBooksDTO {



    private Long id;

    private Long bookId;

    private Long usersId;

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
