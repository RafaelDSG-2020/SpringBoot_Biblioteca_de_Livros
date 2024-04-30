package com.crud.cadastrousuario.domain.dto;


import com.crud.cadastrousuario.domain.model.Author;
import com.crud.cadastrousuario.domain.model.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @NotNull(message = "Publisher Company cannot be null")
    @NotBlank(message = "Publisher Company cannot be empty")
    private String publishingCompany;


    @NotNull(message = "ISBN cannot be null")
    @NotBlank(message = "ISBN cannot be empty")
    private String isbn;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishingDate;

    private Integer flag;

    private List<Author> authors;

    public BookDTO(Book book) {

        this.id = book.getId();
        this.title = book.getTitle();
        this.publishingCompany = book.getPublishingCompany();
        this.isbn = book.getIsbn();
        this.publishingDate = book.getPublishingDate();
        this.flag = book.getFlag();
        this.authors = book.getAuthors();

    }
}
