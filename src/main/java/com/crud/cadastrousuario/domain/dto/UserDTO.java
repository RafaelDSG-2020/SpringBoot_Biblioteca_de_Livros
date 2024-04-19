package com.crud.cadastrousuario.domain.dto;

import com.crud.cadastrousuario.domain.validation.ValidEmail;
import com.crud.cadastrousuario.domain.validation.ValidName;
import com.crud.cadastrousuario.domain.validation.ValidPhone;
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
public class UserDTO {

    @ValidName
    private String name;

    @ValidEmail
    private String email;

    @ValidPhone
    private String phone;
}
