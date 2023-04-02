package pl.medical.visits.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.user.Doctor;
import pl.medical.visits.model.user.Speciality;

import java.util.List;

@Getter
@ToString
public class DoctorDTO extends UserDTO{
    private final List<Speciality> specialities;

    public DoctorDTO(Doctor doctor) {
        super(doctor);
        this.specialities = doctor.getSpecialities();
    }
}
