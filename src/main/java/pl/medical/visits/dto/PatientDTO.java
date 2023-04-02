package pl.medical.visits.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.user.Patient;

@Getter
@ToString
public class PatientDTO extends UserDTO{
    private final long assignedDoctorId;

    public PatientDTO(Patient patient) {
        super(patient);
        this.assignedDoctorId = patient.getAssignedDoctor() != null ? patient.getAssignedDoctor().getId() : 0;
    }
}
