package io.samancore.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateLimitValidator implements ConstraintValidator<DateLimit, Date> {

    private DateLimit dateLimit;

    private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssX";
    private final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    public void initialize(DateLimit constraintAnnotation) {
        dateLimit = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }
        if (dateLimit.minDate() != null && !dateLimit.minDate().trim().isEmpty()) {
            Date minDate = getDate(dateLimit.minDate());
            return minDate == null || !date.before(minDate);
        }
        if (dateLimit.maxDate() != null && !dateLimit.maxDate().trim().isEmpty()) {
            Date maxDate = getDate(dateLimit.maxDate());
            return maxDate == null || !date.after(maxDate);
        }
        return true;
    }

    private Date getDate(String dateStr) {
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException ignored) {
        }
        return date;
    }
}