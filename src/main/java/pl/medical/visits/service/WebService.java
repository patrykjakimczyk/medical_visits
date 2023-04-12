package pl.medical.visits.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.medical.visits.config.JwtService;
import pl.medical.visits.dto.DoctorDTO;
import pl.medical.visits.dto.PatientDTO;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Patient;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.user.*;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.repository.UserRepository;
import pl.medical.visits.repository.VisitRepository;
import pl.medical.visits.util.ValidationUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WebService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserAddressRepository userAddressRepository;
    private final VisitRepository visitRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public PatientDTO registerPatient(PatientRequestWrapper requestWrapper)
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
        loginData.setPassword(passwordEncoder.encode(loginData.getPassword()));
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

        return new PatientDTO(givenPatient);
    }

    public DoctorDTO registerDoctor(DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        Doctor givenDoctor = requestWrapper.getDoctor();

        if(!ValidationUtil.isPeselValid(givenDoctor))
            throw new ValidationException("Error has occurred during validation");

        UserLoginData loginData = requestWrapper.getLoginData();
        loginData.setPassword(passwordEncoder.encode(loginData.getPassword()));
        loginData.setUser(givenDoctor);

        try {
            userRepository.save(givenDoctor);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
        return new DoctorDTO(givenDoctor);
    }

    public Page<PatientDTO> getPatients(Map<String, String> reqParams) {
        int offset = Integer.parseInt(reqParams.get("offset"));
        int pageSize = Integer.parseInt(reqParams.get("pageSize"));

        Page<Patient> page = userRepository.findAllPatientsPaging(PageRequest.of(offset, pageSize));
        return page.map(PatientDTO::new);
    }

    public List<DoctorDTO> getAllDoctors() {
        return userRepository.findAllDoctors()
                .stream()
                .map(DoctorDTO::new)
                .collect(Collectors.toList());
    }

    public List<PatientDTO> getAllPatientsForDoctorId(long id) {
        return userRepository.findPatientsForDoctor(Role.PATIENT.name(), id);
    }

    public AuthenticationResponse loginPatient(UserLoginRequestWrapper userLogin) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(),
                        userLogin.getPassword()
                )
        );
        UserLoginData userLoginData = userLoginRepository.findByEmail(userLogin.getEmail());
        String token = jwtService.generateToken(userLoginData);
        return new AuthenticationResponse(token);
    }
}
