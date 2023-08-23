package pl.medical.visits.exception;

public class NotUniqueValueException extends RuntimeException{
    public NotUniqueValueException() {
        super();
    }
    public NotUniqueValueException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotUniqueValueException(String message) {
        super(message);
    }
    public NotUniqueValueException(Throwable cause) {
        super(cause);
    }
}
