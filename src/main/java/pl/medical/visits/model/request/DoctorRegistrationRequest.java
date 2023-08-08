package pl.medical.visits.model.request;

import lombok.Data;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.entity.user.UserLoginData;

@Data
public final class DoctorRegistrationRequest {
    private final Doctor doctor;
    private final UserLoginData loginData;
    private final Speciality speciality;
}
