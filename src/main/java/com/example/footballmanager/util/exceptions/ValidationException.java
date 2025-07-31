package com.example.footballmanager.util.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String fieldName;
    private final Object invalidValue;

    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.invalidValue = null;
    }

    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = null;
    }

    public ValidationException(String fieldName, Object invalidValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    @Override
    public String toString() {
        if (fieldName != null && invalidValue != null) {
            return String.format("ValidationException{field='%s', invalidValue=%s, message='%s'}",
                    fieldName, invalidValue, getMessage());
        } else if (fieldName != null) {
            return String.format("ValidationException{field='%s', message='%s'}",
                    fieldName, getMessage());
        }
        return super.toString();
    }
}
