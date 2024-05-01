package io.samancore.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateDisableValidator.class)
public @interface DateDisable {
    String message() default "this date is disabled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String disableDates();

    boolean disableWeekends();

    boolean disableWeekdays();
}
