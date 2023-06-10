package pl.medical.visits.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.entity.user.UserLoginData;

@Data
public class DoctorRequestWrapper {
    private final Doctor doctor;
    private final UserLoginData loginData;
    private final Speciality speciality;
}
