package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO {


    private long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Nationality cannot be null")
    @NotBlank(message = "Nationality cannot be empty")
    private String nationality;


    private String flag;

    private List<Book> books;


    public AuthorDTO(Author author) {

        this.id = author.getId();
        this.name = author.getName();
        this.nationality = author.getNationality();
        this.flag = author.getFlag();
        this.books = author.getBooks();


    }

}
