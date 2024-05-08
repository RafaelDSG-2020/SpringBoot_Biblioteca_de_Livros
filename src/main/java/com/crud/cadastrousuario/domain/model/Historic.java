package com.crud.cadastrousuario.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name = "HISTORIC")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Historic {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book bookID;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User usersID;

    @Column(name = "DATE_REMOVAL", length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateRemoval;

    @Column(name = "DATE_RETURN_EXPECTED" , length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateReturnExpected;

    @Column(name = "DATE_RETURN_USER" , length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dateReturnUser;

    @Column(name = "FINE_BOOK" , length = 50, nullable = false)
    private Double fineBook;

    public Historic(LoanOfBooks loan) {

        this.bookID = loan.getBookID();
        this.usersID = loan.getUsersID();
        this.dateRemoval = loan.getDateRemoval();
        this.dateReturnExpected = loan.getDateReturn();
        this.dateReturnUser = LocalDate.now();
        this.fineBook = 0.0;


    }
}
