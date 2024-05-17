package com.crud.cadastrousuario.domain.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "BOOK")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID" , insertable=false, updatable=false)
    private Long id;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "PUBLISHING_COMPANY", length = 50, nullable = false)
    private String publishingCompany;

    @Column(name = "ISBN", length = 50, nullable = false, unique = true)
    private String isbn;

    @Column(name = "PUBLISHING_DATE", length = 50, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate publishingDate;

    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

  //  private Long authorsID;

  //  private Long loanID;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL )
    @JoinTable(name = "BOOK_AUTHOR" ,
            joinColumns = @JoinColumn(name = "BOOK_ID") ,
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private List<Author> authors = new ArrayList<>();



//    @JsonIgnore
//    @OneToMany(mappedBy = "bookID" , fetch = FetchType.LAZY)
//    private List<LoanOfBooks> loan;






}
