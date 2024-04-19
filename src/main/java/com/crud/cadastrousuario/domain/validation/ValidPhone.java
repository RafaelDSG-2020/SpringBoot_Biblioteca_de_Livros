package com.crud.cadastrousuario.domain.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@NotBlank(message = "O numero não pode ser em branco")
//@NotNull(message = "O numero não pode ser nulo")
//@Size(min = 11 , max = 13, message = "O numero deve estar entre 11 e 13  digitos")
//@Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Você deve colocar apenas numeros")
@Constraint(validatedBy = ValidPhoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    String message() default "Nome inválido";
        Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
