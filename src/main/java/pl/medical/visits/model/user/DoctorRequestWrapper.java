package pl.medical.visits.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestWrapper {
    private Doctor doctor;
    private UserLoginData loginData;
    private Speciality speciality;
}
