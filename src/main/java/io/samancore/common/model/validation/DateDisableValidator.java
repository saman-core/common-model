package io.samancore.common.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateDisableValidator implements ConstraintValidator<DateDisable, Date> {

    private static final String dateFormat = "yyyy-MM-dd";
    private final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

    private DateDisable dateDisable;

    public void initialize(DateDisable constraintAnnotation) {
        dateDisable = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }
        if (dateDisable.disableDates() != null || dateDisable.disableWeekdays() || dateDisable.disableWeekends()) {
            var disableDatesArr = dateDisable.disableDates() != null && !dateDisable.disableDates().isEmpty() ? dateDisable.disableDates().split(",") : null;
            if (disableDatesArr != null && disableDatesArr.length > 0) {
                var dateDisableds = Arrays.stream(disableDatesArr).map(this::getDateDisabled).toList();
                if (dateDisableds.stream().anyMatch(dateDisabled -> evaluateDateIsDisabled(date, dateDisabled))) {
                    return false;
                }
            }
            var daysWeekEnd = List.of(Calendar.SATURDAY, Calendar.SUNDAY);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            var day = cal.get(Calendar.DAY_OF_WEEK);
            return (!dateDisable.disableWeekdays() || daysWeekEnd.contains(day)) && (!dateDisable.disableWeekends() || !daysWeekEnd.contains(day));
        }
        return true;
    }

    private boolean evaluateDateIsDisabled(Date date, Pair<Date, Date> dateDisabled) {
        return !date.before(dateDisabled.getLeft()) && !date.after(dateDisabled.getRight());
    }

    private Pair<Date, Date> getDateDisabled(String dateStr) {


        var dateSplited = dateStr.split("-");
        if (dateSplited.length > 3) {
            var minDateDisabledStr = dateSplited[0].concat("-").concat(dateSplited[1]).concat("-").concat(dateSplited[2]);
            var minDate = getDate(minDateDisabledStr);
            minDate = getDateWithMinTime(minDate);
            var maxDateDisabledStr = dateSplited[3].concat("-").concat(dateSplited[4]).concat("-").concat(dateSplited[5]);
            var maxDate = getDate(maxDateDisabledStr);
            maxDate = getDateWithMaxTime(maxDate);
            return Pair.of(minDate, maxDate);
        } else {
            var dateDisabledStr = dateSplited[0].concat("-").concat(dateSplited[1]).concat("-").concat(dateSplited[2]);
            var dateDisabled = getDate(dateDisabledStr);
            var minDate = getDateWithMinTime(dateDisabled);
            var maxDate = getDateWithMaxTime(dateDisabled);
            return Pair.of(minDate, maxDate);
        }
    }

    private static Date getDateWithMinTime(Date date) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    private static Date getDateWithMaxTime(Date date) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
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