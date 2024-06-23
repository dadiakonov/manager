package engineer.dima.manager.error;

import engineer.dima.manager.error.validation.ValidationErrorFactory;
import engineer.dima.manager.error.validation.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
    private final static String DEFAULT_ERROR_MESSAGE = "Something went wrong.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(WebExchangeBindException.class)
    public ValidationErrorResponse handleWebExchangeBindException(WebExchangeBindException exception) {
        log.debug("WebExchangeBindException", exception);

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return new ValidationErrorResponse(ValidationErrorFactory.create(fieldErrors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse handleWebExchangeBindException(ResponseStatusException exception) {
        log.debug("ResponseStatusException", exception);

        return new ErrorResponse(exception.getReason());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public ErrorResponse handleWebExchangeBindException(Throwable throwable) {
        log.error("Unhandled exception", throwable);

        return new ErrorResponse(DEFAULT_ERROR_MESSAGE);
    }
}
