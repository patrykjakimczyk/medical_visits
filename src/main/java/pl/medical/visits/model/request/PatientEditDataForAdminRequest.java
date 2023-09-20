package pl.medical.visits.model.request;

import lombok.*;
import pl.medical.visits.model.enums.Role;

@Getter
@Setter
public final class PatientEditDataForAdminRequest extends PatientEditDataForPatientRequest{
    private long assignedDoctorId;
}
