package com.crud.cadastrousuario.exemplos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {
    @Override
    public void initialize(ValidPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null) {
            context.buildConstraintViolationWithTemplate("O número não pode ser nulo")
                    .addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("O número não pode ser em branco")
                    .addConstraintViolation();
            return false;
        }
        if (value.trim().length() < 11 || value.trim().length() > 13) {
            context.buildConstraintViolationWithTemplate("O número deve estar entre 11 e 13 dígitos")
                    .addConstraintViolation();
            return false;
        }
        if (!value.matches("\\d+")) {
            context.buildConstraintViolationWithTemplate("Você deve colocar apenas números")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
