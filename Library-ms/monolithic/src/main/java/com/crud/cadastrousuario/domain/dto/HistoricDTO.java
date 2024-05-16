package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Book;
import com.crud.cadastrousuario.domain.model.Historic;
import com.crud.cadastrousuario.domain.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
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
public class HistoricDTO {




    private Long id;


    private Long bookID;


    private Long usersID;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateRemoval;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateReturnExpected;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateReturnUser;


    private Double fineBook;

    public HistoricDTO(Historic historic) {

        this.bookID = historic.getBookID().getId();
        this.usersID = historic.getUsersID().getId();
        this.dateRemoval = historic.getDateRemoval();
        this.dateReturnExpected = historic.getDateReturnExpected();
        this.dateReturnUser = historic.getDateReturnUser();
        this.fineBook = historic.getFineBook();

    }
}
