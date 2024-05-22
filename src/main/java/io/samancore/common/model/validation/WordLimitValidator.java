package io.samancore.common.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WordLimitValidator implements ConstraintValidator<WordLimit, String> {

    private WordLimit wordLimit;

    public void initialize(WordLimit constraintAnnotation) {
        wordLimit = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        var count = countWords(value);
        return wordLimit.min() <= count && wordLimit.max() >= count;
    }

    private int countWords(String value) {
        final String[] words = value.split("[\\pP\s&&[^']]+");
        return words.length;
    }
}