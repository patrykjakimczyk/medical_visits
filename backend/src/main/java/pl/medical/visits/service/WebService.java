package pl.medical.visits.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.medical.visits.model.Patient;
import pl.medical.visits.model.User;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.repository.WebRepository;

@Service
@AllArgsConstructor
public class WebService {

    private WebRepository webRepository;

    public Patient registerPatient(Patient patient) {
        patient.setRole(Role.PATIENT);
        return (Patient) webRepository.save(patient);
    }
}
