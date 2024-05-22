package com.crud.cadastrousuario.domain.dto;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {


    private Long id;


    private String title;


    private String publishingCompany;


    private String isbn;


    @Temporal(TemporalType.DATE)
    private LocalDate publishingDate;


    private Integer flag;




}
