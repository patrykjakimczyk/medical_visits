package pl.medical.visits.service;

import org.springframework.stereotype.Service;
import pl.medical.visits.model.request.DoctorRegistrationRequest;
import pl.medical.visits.model.request.PatientRegistrationRequest;
import pl.medical.visits.model.request.UserLoginRequest;
import pl.medical.visits.model.response.AuthenticationResponse;

@Service
public interface RegistrationService {
    AuthenticationResponse registerPatient(PatientRegistrationRequest requestWrapper);

    AuthenticationResponse registerDoctor(DoctorRegistrationRequest requestWrapper);

    AuthenticationResponse loginUser(UserLoginRequest userLogin);
}
