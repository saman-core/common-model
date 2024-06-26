package io.samancore.common.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxByteValidator.class)
public @interface MaxByte {
    String message() default "size of the element should be small in bytes ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
