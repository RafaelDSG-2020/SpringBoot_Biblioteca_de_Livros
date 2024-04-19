package com.crud.cadastrousuario.domain.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {

    public void initialize(ValidName constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (value == null) {
            context.buildConstraintViolationWithTemplate("Nome não pode ser nulo")
                    .addConstraintViolation();
            return false;
        }
        if (value.trim().isEmpty()) {
            context.buildConstraintViolationWithTemplate("Nome não pode ser vazio")
                    .addConstraintViolation();
            return false;
        }
        if (value.trim().length() < 3 || value.trim().length() > 50) {
            context.buildConstraintViolationWithTemplate("O número de caracteres deve estar entre 3 e 50 caracteres")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
