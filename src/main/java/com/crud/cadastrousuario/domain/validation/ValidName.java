package com.crud.cadastrousuario.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@NotNull(message = "Nome não pode ser nulo")
//@NotBlank(message = "Nome não pode ser vazio")
//@Size(min = 3, max = 50, message = "O número de caracteres deve estar entre 3 e 50 caracteres")
@Constraint(validatedBy = ValidNameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {

    String message() default "Nome inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

