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
import pl.medical.visits.exception.WrongRequestParametersException;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Patient;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.repository.UserRepository;
import pl.medical.visits.repository.VisitRepository;
import pl.medical.visits.util.StringUtil;
import pl.medical.visits.util.ValidationService;

import java.util.Map;

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
    private final ValidationService validationService;

    public PatientDTO registerPatient(PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        Patient givenPatient = requestWrapper.getPatient();
        validationService.validateUser(givenPatient);

        givenPatient.setFirstName(
                StringUtil.firstCapital(givenPatient.getFirstName())
        );
        givenPatient.setLastName(
                StringUtil.firstCapital(givenPatient.getLastName())
        );

        UserLoginData loginData = requestWrapper.getLoginData();
        validationService.validateUserEmail(loginData);
        loginData.setPassword(passwordEncoder.encode(loginData.getPassword()));
        loginData.setUser(givenPatient);

        UserAddressData addressData = requestWrapper.getAddressData();
        validationService.validateUserAddress(addressData);
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
        validationService.validateUser(givenDoctor);

        UserLoginData loginData = requestWrapper.getLoginData();
        validationService.validateUserEmail(loginData);
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
        int offset;
        int pageSize;

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Wrong request parameters");
        }

        return userRepository
                .findAllPatientsPaging(PageRequest.of(offset, pageSize))
                .map(PatientDTO::new);
    }

    public Page<DoctorDTO> getDoctors(Map<String, String> reqParams) {
        int offset;
        int pageSize;

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Wrong request parameters");
        }

        return userRepository
                .findAllDoctorsPaging(PageRequest.of(offset, pageSize))
                .map(DoctorDTO::new);
    }

    public Page<PatientDTO> getAllPatientsForDoctorId(Map<String, String> reqParams) {
        int offset;
        int pageSize;
        long id;

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
            id = Long.getLong(reqParams.get("id"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Wrong request parameters");
        }

        return userRepository
                .findPatientsForDoctor(id, PageRequest.of(offset, pageSize))
                .map(PatientDTO::new);
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
