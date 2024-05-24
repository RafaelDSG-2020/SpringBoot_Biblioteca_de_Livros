package com.crud.cadastrousuario.domain.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {


    private Long id;
    private String name;
    private String nationality;
    private Integer flag;




}
