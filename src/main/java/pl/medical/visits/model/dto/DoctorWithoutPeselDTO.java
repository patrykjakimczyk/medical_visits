package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.enums.Role;

import java.util.List;

@Getter
@ToString
public class DoctorWithoutPeselDTO {
    private final long id;
    private final Role role;
    private final String firstName;
    private final String lastName;
    private final String phoneNr;
    private final String email;
    private final List<Speciality> specialities;

    public DoctorWithoutPeselDTO(Doctor doctor, String email) {
        this.id = doctor.getId();
        this.role = doctor.getRole();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.phoneNr = doctor.getPhoneNr();
        this.email = email;
        this.specialities = doctor.getSpecialities();
    }
}
