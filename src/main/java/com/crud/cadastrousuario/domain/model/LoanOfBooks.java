package com.crud.cadastrousuario.domain.model;


import com.crud.cadastrousuario.domain.dto.LoanOfBooksDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "LOAN_OF_BOOKS")
public class LoanOfBooks {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID")
    private Book bookID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERS_ID")
    private User usersID;

    @Column(name = "DATE_REMOVAL", length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateRemoval;

    @Column(name = "DATE_RETURN" , length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateReturn;



    public LoanOfBooks() {

        this.dateRemoval = LocalDate.now();
        this.dateReturn = dateRemoval.plusDays(30);

    }
}
