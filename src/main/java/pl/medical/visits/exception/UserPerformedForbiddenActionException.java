package pl.medical.visits.exception;

public class UserPerformedForbiddenActionException extends RuntimeException{
    public UserPerformedForbiddenActionException() {
        super();
    }
    public UserPerformedForbiddenActionException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserPerformedForbiddenActionException(String message) {
        super(message);
    }
    public UserPerformedForbiddenActionException(Throwable cause) {
        super(cause);
    }
}
