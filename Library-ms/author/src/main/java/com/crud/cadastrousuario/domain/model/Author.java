package com.crud.cadastrousuario.domain.model;

import com.crud.cadastrousuario.domain.dto.AuthorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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

    @Column(name = "FLAG" , nullable = false)
    private Integer flag;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors" , cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    public Author(AuthorDTO author) {

        this.name = author.getName();
        this.nationality = author.getNationality();
        this.flag = 1;
        this.books = author.getBooks();
    }
}
