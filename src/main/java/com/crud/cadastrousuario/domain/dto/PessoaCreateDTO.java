package com.crud.cadastrousuario.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
public class PessoaCreateDTO {

    @NotBlank(message = "Nome não pode ser nulo")
    @Size(min = 3 ,message = "O numero minimo é 3 caracteres")
    @Size(max = 50 , message = "O numero maximo é 50 caracteres")
    private String nome;
    @Email(message = "Email invalido Colocar um email Valido")
    private String email;
    @Size(min = 11 , message = "O numero minimo é 11 Digitos")
    @Size(max = 13 , message = "O numero maximo é 13 Digitos")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Você deve colocar apenas numeros")
    private String telefone;
}
