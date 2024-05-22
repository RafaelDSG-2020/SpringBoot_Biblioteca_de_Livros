package com.crud.cadastrousuario.domain.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
//@Embeddable
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
