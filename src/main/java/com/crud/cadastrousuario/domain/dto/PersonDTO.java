package com.crud.cadastrousuario.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Table(name = "Pessoa",uniqueConstraints={@UniqueConstraint(columnNames={"EMAIL"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {

    private Long id;
    @NotNull(message = "Nome nao pode ser nulo")
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3 , max = 50,message = "O numero de caracteres deve estar entre 3 e 50 caractesres")
    private String name;
    @NotNull(message = "email nao pode ser nulo")
    @Email(message = "Email invalido Colocar um email Valido")
    private String email;
    @Size(min = 11 , max = 13, message = "O numero deve estar entre 11 e 13  digitos")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Você deve colocar apenas numeros")
    private String phone;
}
