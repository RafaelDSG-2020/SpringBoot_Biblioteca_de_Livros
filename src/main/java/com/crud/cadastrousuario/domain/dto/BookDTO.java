package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {


    private Long id;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be empty")
    @Size(min = 3 , max = 50,message = "The number of characters must be between 3 and 50 characters")
    private String title;

    @NotNull(message = "Publisher cannot be null")
    @NotBlank(message = "Publisher cannot be empty")
    private String publishing_company;


    @NotNull(message = "Publication date cannot be null")
    @NotBlank(message = "Publication date cannot be empty")
    private String published_date;


    @NotNull(message = "ISBN cannot be null")
    @NotBlank(message = "ISBN cannot be empty")
    private String ISBN;

    public BookDTO(Book book) {

        this.id = book.getId();
        this.title = book.getTitle();
        this.publishing_company = book.getPublishing_company();
    }
}
