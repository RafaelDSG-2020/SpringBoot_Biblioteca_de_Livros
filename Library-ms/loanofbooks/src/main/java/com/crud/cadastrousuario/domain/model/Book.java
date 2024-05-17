package com.crud.cadastrousuario.domain.model;



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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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




}
