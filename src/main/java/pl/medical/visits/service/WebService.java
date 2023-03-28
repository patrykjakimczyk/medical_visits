package pl.medical.visits.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.user.*;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.repository.UserRepository;
import pl.medical.visits.repository.VisitRepository;
import pl.medical.visits.util.ValidationUtil;

import java.util.List;

@Service
@AllArgsConstructor
public class WebService {

    private UserRepository userRepository;
    private UserLoginRepository userLoginRepository;
    private UserAddressRepository userAddressRepository;
    private VisitRepository visitRepository;

    public Patient registerPatient(PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        Patient givenPatient = requestWrapper.getPatient();

        givenPatient.setFirstName(
                ValidationUtil.firstCapital(givenPatient.getFirstName())
        );
        givenPatient.setLastName(
                ValidationUtil.firstCapital(givenPatient.getLastName())
        );

        if(!ValidationUtil.isPeselValid(givenPatient))
            throw new ValidationException("Error has occurred during validation");

        UserLoginData loginData = requestWrapper.getLoginData();
        loginData.setUser(givenPatient);

        UserAddressData addressData = requestWrapper.getAddressData();
        addressData.setUser(givenPatient);

        try {
            userLoginRepository.save(loginData);
            userAddressRepository.save(addressData);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }

        return givenPatient;
    }

    public Doctor registerDoctor(DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        Doctor givenDoctor = requestWrapper.getDoctor();

        if(!ValidationUtil.isPeselValid(givenDoctor))
            throw new ValidationException("Error has occurred during validation");

        UserLoginData loginData = requestWrapper.getLoginData();
        loginData.setUser(givenDoctor);

        try {
            return userRepository.save(givenDoctor);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
    }

    public List<User> getAllUsersByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    public List<User> getAllPatientsForDoctorId(long id) {
        return userRepository.findPatientsForDoctor(Role.PATIENT.name(), id);
    }
}
