package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Doctor;

@Getter
@ToString
public class AssignedDoctorDTO {
    private final long id;
    private final String firstName;
    private final String lastName;

    public AssignedDoctorDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
    }
}
