package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Speciality;

import java.util.List;

@Getter
@ToString
public class DoctorDTO extends UserDTO{
    private final String email;
    private final List<Speciality> specialities;

    public DoctorDTO(Doctor doctor, String email) {
        super(doctor);
        this.specialities = doctor.getSpecialities();
        this.email = email;
    }
}
