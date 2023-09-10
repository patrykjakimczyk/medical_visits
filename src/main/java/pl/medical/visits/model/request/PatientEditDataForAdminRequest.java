package pl.medical.visits.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.medical.visits.model.enums.Role;

@Getter
@Setter
public final class PatientEditDataForAdminRequest extends PatientEditDataForPatientRequest{
    private final Role role;
    private final String pesel;
    private final long assignedDoctorId;

    public PatientEditDataForAdminRequest(PatientEditDataForPatientRequest patientEditDataForPatientRequest, Role role, String pesel, long assignedDoctorId) {
        super(
                patientEditDataForPatientRequest.getId(),
                patientEditDataForPatientRequest.getFirstName(),
                patientEditDataForPatientRequest.getLastName(),
                patientEditDataForPatientRequest.getPhoneNr(),
                patientEditDataForPatientRequest.getEmail(),
                patientEditDataForPatientRequest.getCountry(),
                patientEditDataForPatientRequest.getCity(),
                patientEditDataForPatientRequest.getStreet(),
                patientEditDataForPatientRequest.getHouseNr(),
                patientEditDataForPatientRequest.getApartmentNr(),
                patientEditDataForPatientRequest.getPostalCode()
        );
        this.role = role;
        this.pesel = pesel;
        this.assignedDoctorId = assignedDoctorId;
    }
}
