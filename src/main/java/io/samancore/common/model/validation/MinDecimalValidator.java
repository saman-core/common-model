package io.samancore.common.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MinDecimalValidator implements ConstraintValidator<MinDecimal, Object> {

    private MinDecimal minDecimal;

    public void initialize(MinDecimal constraintAnnotation) {
        minDecimal = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object valueInput, ConstraintValidatorContext constraintValidatorContext) {
        if (valueInput == null) {
            return true;
        }
        var minValue = new BigDecimal(minDecimal.value());
        return (!(valueInput instanceof BigDecimal) || minValue.compareTo(((BigDecimal) valueInput)) <= 0)
                && (!(valueInput instanceof Double) || Double.compare(minValue.doubleValue(), ((Double) valueInput)) <= 0);
    }
}