package com.crud.cadastrousuario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "BOOK_AUTHOR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookAuthor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BOOK_ID")
    private Long bookID;

    @Column(name = "AUHTOR_ID")
    private Long authorID;
}
