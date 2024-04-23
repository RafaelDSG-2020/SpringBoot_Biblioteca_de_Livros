package com.crud.cadastrousuario.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Books")
@Table(name = "books")
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
    private String publishing_company;
    @Column(name = "PUBLISHING_DATE", length = 50, nullable = false)
    private String published_date;
    @Column(name = "ISBN", length = 50, nullable = false, unique = true)
    private String ISBN;
}
