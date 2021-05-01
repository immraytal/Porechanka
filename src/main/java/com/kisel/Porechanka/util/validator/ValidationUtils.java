package com.kisel.Porechanka.util.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Objects;

public class ValidationUtils {

    public static String getValidationErrorsAsString(BindingResult result) {
        StringBuilder stringBuilder = new StringBuilder("Errors: ");
        for (ObjectError error : result.getAllErrors()) {
            stringBuilder.append(Objects.requireNonNull(error.getArguments())[0].toString().split("default message ")[1]);
            stringBuilder.append(" ");
            stringBuilder.append(error.getDefaultMessage());
            stringBuilder.append("; ");
        }
        return stringBuilder.toString();
    }
}
