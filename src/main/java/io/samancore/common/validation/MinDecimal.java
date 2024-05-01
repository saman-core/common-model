package io.samancore.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinDecimalValidator.class)
public @interface MinDecimal {
    String message() default "this number should be greater than min value ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();
}
