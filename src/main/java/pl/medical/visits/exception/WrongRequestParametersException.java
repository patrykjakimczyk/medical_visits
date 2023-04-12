package pl.medical.visits.exception;

public class WrongRequestParametersException extends RuntimeException{
    public WrongRequestParametersException() {
        super();
    }
    public WrongRequestParametersException(String message, Throwable cause) {
        super(message, cause);
    }
    public WrongRequestParametersException(String message) {
        super(message);
    }
    public WrongRequestParametersException(Throwable cause) {
        super(cause);
    }
}
