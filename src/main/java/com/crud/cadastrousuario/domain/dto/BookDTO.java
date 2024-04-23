package com.crud.cadastrousuario.domain.dto;


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


    @NotNull(message = "titulo nao pode ser nulo")
    @NotBlank(message = "titulo não pode ser vazio")
    @Size(min = 3 , max = 50,message = "O numero de caracteres deve estar entre 3 e 50 caractesres")
    private String title;

    @NotNull(message = "Editora nao pode ser nulo")
    @NotBlank(message = "Editora não pode ser vazio")
    private String publishing_company;


    @NotNull(message = "Data de publicação não pode ser nulo")
    @NotBlank(message = "Data de publicação não pode ser vazio")
    private String published_date;


    @NotNull(message = "ISBN  não pode ser nulo")
    @NotBlank(message = "ISBN não pode ser vazio")
    private String ISBN;
}
