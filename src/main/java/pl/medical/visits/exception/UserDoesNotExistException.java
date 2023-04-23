package pl.medical.visits.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException() {
        super();
    }
    public UserDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserDoesNotExistException(String message) {
        super(message);
    }
    public UserDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
