package com.crud.cadastrousuario.domain.model;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "AUTHOR")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME" , length = 50, nullable = false)
    private String name;
    @Column(name = "NATIONALITY" , length = 50, nullable = false)
    private String nationality;


    public Author(AuthorDTO author) {

        this.name = author.getName();
        this.nationality= author.getNationality();
    }
}
