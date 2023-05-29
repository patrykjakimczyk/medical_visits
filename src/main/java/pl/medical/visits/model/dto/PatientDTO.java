package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Patient;

@Getter
@ToString
public class PatientDTO extends UserDTO{
    private final AssignedDoctorDTO assignedDoctor;

    public PatientDTO(Patient patient, String email) {
        super(patient, email);
        this.assignedDoctor = patient.getAssignedDoctor() != null
                ? new AssignedDoctorDTO(patient.getAssignedDoctor())
                : null;
    }
}
