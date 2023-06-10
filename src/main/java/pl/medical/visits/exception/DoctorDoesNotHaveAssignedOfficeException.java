package pl.medical.visits.exception;

public class DoctorDoesNotHaveAssignedOfficeException extends RuntimeException{
    public DoctorDoesNotHaveAssignedOfficeException() {
        super();
    }
    public DoctorDoesNotHaveAssignedOfficeException(String message, Throwable cause) {
        super(message, cause);
    }
    public DoctorDoesNotHaveAssignedOfficeException(String message) {
        super(message);
    }
    public DoctorDoesNotHaveAssignedOfficeException(Throwable cause) {
        super(cause);
    }
}
