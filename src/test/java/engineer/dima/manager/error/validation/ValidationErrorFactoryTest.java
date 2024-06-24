package engineer.dima.manager.error.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationErrorFactoryTest {
    private static final String FIELD_1 = "field_1";
    private static final String FIELD_2 = "field_2";
    private static final String DEFAULT_MESSAGE_1 = "default message 1";
    private static final String DEFAULT_MESSAGE_2 = "default message 2";

    @Test
    public void createReturnsValidationErrorWithFieldAndDefaultMessage() {
        FieldError fieldError = getFieldErrorMock(FIELD_1, DEFAULT_MESSAGE_1);

        ValidationError validationError = ValidationErrorFactory.create(fieldError);

        assertEquals(FIELD_1, validationError.field());
        assertEquals(DEFAULT_MESSAGE_1, validationError.message());
    }

    @Test
    public void createReturnsValidationErrorWithFieldAndInvalidMessage() {
        FieldError fieldError = getFieldErrorMock(FIELD_1, null);

        ValidationError validationError = ValidationErrorFactory.create(fieldError);

        assertEquals(FIELD_1, validationError.field());
        assertEquals(FIELD_1 + " is invalid", validationError.message());
    }

    @Test
    public void createReturnsListOfValidationErrors() {
        FieldError fieldError1 = getFieldErrorMock(FIELD_1, DEFAULT_MESSAGE_1);
        FieldError fieldError2 = getFieldErrorMock(FIELD_2, DEFAULT_MESSAGE_2);

        List<FieldError> fieldErrors = List.of(fieldError1, fieldError2);

        List<ValidationError> validationErrors = ValidationErrorFactory.create(fieldErrors);

        assertEquals(2, validationErrors.size());
        assertEquals(FIELD_1, validationErrors.get(0).field());
        assertEquals(DEFAULT_MESSAGE_1, validationErrors.get(0).message());
        assertEquals(FIELD_2, validationErrors.get(1).field());
        assertEquals(DEFAULT_MESSAGE_2, validationErrors.get(1).message());
    }

    private static FieldError getFieldErrorMock(String field, String defaultMessage) {
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getField())
                .thenReturn(field);
        when(fieldError.getDefaultMessage())
                .thenReturn(defaultMessage);

        return fieldError;
    }
}
