package io.samancore.common.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.charset.StandardCharsets;

public class MaxByteValidator implements ConstraintValidator<MaxByte, Object> {

    private MaxByte maxByte;

    public void initialize(MaxByte constraintAnnotation) {
        maxByte = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object valueInput, ConstraintValidatorContext constraintValidatorContext) {
        if (valueInput == null || valueInput.toString().isEmpty()) {
            return true;
        }
        var size = valueInput.toString().getBytes(StandardCharsets.UTF_8).length;
        return size<maxByte.value();
    }
}