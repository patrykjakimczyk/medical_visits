package pl.medical.visits.exception;

public class ValidationException extends Exception{
    public ValidationException() {
        super();
    }
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
