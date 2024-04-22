package com.crud.cadastrousuario.exemplos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value == null) {
            context.buildConstraintViolationWithTemplate("Email não pode ser nulo")
                    .addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Email não pode ser em branco")
                    .addConstraintViolation();
            return false;
        }
        if (!value.contains("@") || !value.contains(".")) {
            context.buildConstraintViolationWithTemplate("Email inválido. Colocar um email válido")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
