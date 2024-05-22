package io.samancore.common.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateLimitValidator.class)
public @interface DateLimit {
    String message() default "this date is out of valid dates";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String minDate();

    String maxDate();
}
