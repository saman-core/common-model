package io.samancore.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MaxDecimalValidator implements ConstraintValidator<MaxDecimal, Object> {

    private MaxDecimal maxDecimal;

    public void initialize(MaxDecimal constraintAnnotation) {
        maxDecimal = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object valueInput, ConstraintValidatorContext constraintValidatorContext) {
        if (valueInput == null) {
            return true;
        }
        var maxValue = new BigDecimal(maxDecimal.value());
        return (!(valueInput instanceof BigDecimal) || maxValue.compareTo(((BigDecimal) valueInput)) >= 0)
                && (!(valueInput instanceof Double) || Double.compare(maxValue.doubleValue(), ((Double) valueInput)) >= 0);
    }
}