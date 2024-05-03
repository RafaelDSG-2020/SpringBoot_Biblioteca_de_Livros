package com.crud.cadastrousuario.domain.model;


import com.crud.cadastrousuario.domain.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Column(name = "ID")
    private Long id;
    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;
    @Column(name = "PUBLISHING_COMPANY", length = 50, nullable = false)
    private String publishingCompany;
    @Column(name = "ISBN", length = 50, nullable = false, unique = true)
    private String isbn;
    @Column(name = "PUBLISHING_DATE", length = 50, nullable = false)
    private LocalDate publishingDate;
    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR" ,
            joinColumns = @JoinColumn(name = "BOOK_ID") ,
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private List<Author> authors = new ArrayList<>();



//    @ManyToOne
//    @JoinColumn(name = "stock_id")
//    private Stock stock;




    public Book(BookDTO bookCreateDTO) {

        this.title = bookCreateDTO.getTitle();
        this.publishingCompany = bookCreateDTO.getPublishingCompany();
        this.isbn = bookCreateDTO.getIsbn();
        this.publishingDate = bookCreateDTO.getPublishingDate();
        this.flag = 1;
        this.authors = bookCreateDTO.getAuthors();

    }
}
