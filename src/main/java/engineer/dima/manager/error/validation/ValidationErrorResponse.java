package engineer.dima.manager.error.validation;

import java.util.List;

public record ValidationErrorResponse(List<ValidationError> errors) {
}
