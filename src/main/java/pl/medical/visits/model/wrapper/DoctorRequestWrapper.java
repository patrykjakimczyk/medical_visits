package pl.medical.visits.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.entity.user.UserLoginData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestWrapper {
    private Doctor doctor;
    private UserLoginData loginData;
    private Speciality speciality;
}
