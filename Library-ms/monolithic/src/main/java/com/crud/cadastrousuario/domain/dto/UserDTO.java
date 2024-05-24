package com.crud.cadastrousuario.domain.dto;

import com.crud.cadastrousuario.domain.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {


    private long id;

    @NotBlank(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be null")
    @Size(min = 3 , max = 50,message = "The number of characters must be between 3 and 50 characters")
    private String name;


    @NotBlank(message = "Email cannot be empty")
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email Enter a valid email")
    private String email;


    @NotBlank(message = "The Phone cannot be empty")
    @NotNull(message = "The number cannot be null")
    @Size(min = 11 , max = 13, message = "The number must be between 11 and 13 digits")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "You must only enter numbers")
    private String phone;


    private Integer flag;


    public UserDTO(User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.flag = user.getFlag();
    }
}
