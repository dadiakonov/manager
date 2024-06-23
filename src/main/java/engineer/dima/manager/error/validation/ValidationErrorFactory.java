package engineer.dima.manager.error.validation;

import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationErrorFactory {
    public static ValidationError create(FieldError fieldError) {
        return new ValidationError(fieldError.getField(),
                fieldError.getDefaultMessage() != null
                        ? fieldError.getDefaultMessage()
                        : fieldError.getField() + " is invalid");
    }

    public static List<ValidationError> create(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(ValidationErrorFactory::create)
                .toList();
    }
}
