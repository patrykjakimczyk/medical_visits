package pl.medical.visits.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@ResponseStatus
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionMessage> validation(ValidationException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(NotUniqueValueException.class)
    public ResponseEntity<ExceptionMessage> sqlException(NotUniqueValueException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(WrongRequestParametersException.class)
    public ResponseEntity<ExceptionMessage> wrongParametersException(
            WrongRequestParametersException exception,
            WebRequest webRequest
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ExceptionMessage> userDoesNotExist(UserDoesNotExistException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(UserPerformedForbiddenActionException.class)
    public ResponseEntity<ExceptionMessage> userPerformedForbiddenAction(
            UserPerformedForbiddenActionException exception, WebRequest webRequest
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionMessage(exception.getMessage()));
    }
}
