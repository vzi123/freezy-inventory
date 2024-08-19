package freezy.utils;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FreazyValidFieldValidator implements ConstraintValidator<FreazyValidField, String> {

    private boolean numbersOnly;
    private boolean alphanumeric;
    private boolean notBlank;
    private int minLength;
    private boolean isEmail;

    @Override
    public void initialize(FreazyValidField constraintAnnotation) {
        this.numbersOnly = constraintAnnotation.numbersOnly();
        this.alphanumeric = constraintAnnotation.alphanumeric();
        this.notBlank = constraintAnnotation.notBlank();
        this.minLength = constraintAnnotation.minLength();
        this.isEmail = constraintAnnotation.isEmail();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return !notBlank;  // If field can be blank, null is allowed
        }

        if (notBlank && value.trim().isEmpty()) {
            return false; // Field cannot be blank
        }

        if (numbersOnly && !value.matches("\\d+")) {
            return false; // Field must be numbers only
        }

        if (alphanumeric && !value.matches("[a-zA-Z0-9]+")) {
            return false; // Field must be alphanumeric
        }

        if (value.length() < minLength) {
            return false; // Field must meet minimum length
        }

        if(isEmail && !value.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")){
            return false; // Checking Email format
        }

        return true;
    }
}

