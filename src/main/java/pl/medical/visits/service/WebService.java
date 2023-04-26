package pl.medical.visits.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.medical.visits.config.JwtService;
import pl.medical.visits.exception.*;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.repository.UserRepository;
import pl.medical.visits.repository.VisitRepository;
import pl.medical.visits.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public AuthenticationResponse registerPatient(PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        Patient givenPatient = requestWrapper.getPatient();
        validationService.validateUser(givenPatient);

        givenPatient.setFirstName(StringUtil.firstCapital(givenPatient.getFirstName()));
        givenPatient.setLastName(StringUtil.firstCapital(givenPatient.getLastName()));

        UserLoginData loginData = requestWrapper.getLoginData();
        String password = loginData.getPassword();
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
        UserLoginRequestWrapper userLoginRequestWrapper = new UserLoginRequestWrapper(loginData.getEmail(), password);
        return loginUser(userLoginRequestWrapper);
    }

    public AuthenticationResponse registerDoctor(DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        Doctor givenDoctor = requestWrapper.getDoctor();
        validationService.validateUser(givenDoctor);

        givenDoctor.setFirstName(StringUtil.firstCapital(givenDoctor.getFirstName()));
        givenDoctor.setLastName(StringUtil.firstCapital(givenDoctor.getLastName()));

        UserLoginData loginData = requestWrapper.getLoginData();
        String password = loginData.getPassword();
        validationService.validateUserEmail(loginData);
        loginData.setPassword(passwordEncoder.encode(loginData.getPassword()));
        loginData.setUser(givenDoctor);

        try {
            userLoginRepository.save(loginData);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
        UserLoginRequestWrapper userLoginRequestWrapper = new UserLoginRequestWrapper(loginData.getEmail(), password);
        return loginUser(userLoginRequestWrapper);

    }

    public Page<PatientDTO> getPatients(Map<String, String> reqParams) {
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);

        if (StringUtil.isStringNotNull(filterKey) && StringUtil.isStringNotNull(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findAllPatientsWithFirstNamePaging(filterKey, pageRequest)
                        .map(PatientDTO::new);
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findAllPatientsWithLastNamePaging(filterKey, pageRequest)
                        .map(PatientDTO::new);
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findAllPatientsWithPeselPaging(filterKey, pageRequest)
                        .map(PatientDTO::new);
            }
        }
        return userRepository
                .findAllPatientsPaging(pageRequest)
                .map(PatientDTO::new);
    }

    public Page<DoctorDTO> getDoctors(Map<String, String> reqParams) {
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);

        if (StringUtil.isStringNotNull(filterKey) && StringUtil.isStringNotNull(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findAllDoctorsWithFirstNamePaging(filterKey, pageRequest)
                        .map(DoctorDTO::new);
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findAllDoctorsWithLastNamePaging(filterKey, pageRequest)
                        .map(DoctorDTO::new);
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findAllDoctorsWithPeselPaging(filterKey, pageRequest)
                        .map(DoctorDTO::new);
            }
        }
        return userRepository
                .findAllDoctorsPaging(pageRequest)
                .map(DoctorDTO::new);
    }

    public Page<PatientDTO> getAllPatientsForDoctorId(Map<String, String> reqParams) {
        int offset;
        int pageSize;
        long id;
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
            id = Long.parseLong(reqParams.get("id"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Invalid request parameters (no paging information or ID)");
        }

        List<Sort.Order> sorts = findSorts(reqParams);
        PageRequest pageRequest;

        if (sorts.isEmpty()) pageRequest = PageRequest.of(offset, pageSize);
        else pageRequest = PageRequest.of(offset, pageSize, Sort.by(sorts));

        if (StringUtil.isStringNotNull(filterKey) && StringUtil.isStringNotNull(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findPatientsWithFirstNameForDoctor(id, filterKey, pageRequest)
                        .map(PatientDTO::new);
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findPatientsWithLastNameForDoctor(id, filterKey, pageRequest)
                        .map(PatientDTO::new);
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findPatientsWithPeselForDoctor(id, filterKey, pageRequest)
                        .map(PatientDTO::new);
            }
        }
        return userRepository
                .findPatientsForDoctor(id, pageRequest)
                .map(PatientDTO::new);
    }

    public Patient getAllPatientsData(String tokenEmail, long id) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, id)) {
            Optional<Patient> patientOptional =  userRepository.findAllById(id);

            return patientOptional.orElseThrow(() ->
                    new UserDoesNotExistException("User with given ID is not a patient"));
        }
        throw new UserPerformedForbiddenActionException("Patient cannot access other users' data!");
    }

    public void updatePatientData(String tokenEmail, Patient patient)
            throws ValidationException, NotUniqueValueException {
        if (this.canAuthUserAccessUserOfId(tokenEmail, patient.getId())
                && userRepository.existsById(patient.getId())) {
            validationService.validateUser(patient);

            patient.setFirstName(StringUtil.firstCapital(patient.getFirstName()));
            patient.setLastName(StringUtil.firstCapital(patient.getLastName()));

            UserAddressData addressData = patient.getAddressData();
            validationService.validateUserAddress(addressData);
            addressData.setUser(patient);

            try {
                userAddressRepository.save(addressData);
            } catch (RuntimeException e) {
                throw new NotUniqueValueException(
                        "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
                );
            }
            return;
        }
        throw new UserPerformedForbiddenActionException("Patient cannot access other users' data!");
    }

    public AuthenticationResponse loginUser(UserLoginRequestWrapper userLogin) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(),
                        userLogin.getPassword()
                )
        );
        UserLoginData userLoginData = userLoginRepository.findByEmail(userLogin.getEmail());
        Optional<User> user = userRepository.findById(userLoginData.getUser().getId());
        String token = jwtService.generateToken(userLoginData);

        if (user.isPresent()) return new AuthenticationResponse(token, user.get().getRole());
        throw new UserDoesNotExistException("User does not exist in database");
    }

    private boolean canAuthUserAccessUserOfId(String email, long id) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getId());

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();

            if (user.getRole().equals(Role.PATIENT)) {
                return user.getId() == id;
            } else {
                return true;
            }
        }
        throw new UserDoesNotExistException("User from given token does not exist");
    }

    private PageRequest createPageRequest(Map<String, String> reqParams) {
        int offset;
        int pageSize;

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Invalid request parameters (no paging information)");
        }

        List<Sort.Order> sorts = this.findSorts(reqParams);

        if (sorts.isEmpty()) return PageRequest.of(offset, pageSize);
        else return PageRequest.of(offset, pageSize, Sort.by(sorts));
    }

    private List<Sort.Order> findSorts(Map<String, String> reqParams) {
        String firstName = "first_name";
        String lastName = "last_name";
        List<Sort.Order> sorts = new ArrayList<>();

        if (reqParams.get(firstName) != null) {
            if (reqParams.get(firstName).equals("DESC"))
                sorts.add(new Sort.Order(Sort.Direction.DESC, firstName));
            else
                sorts.add(new Sort.Order(Sort.Direction.ASC, firstName));
        }

        if (reqParams.get(lastName) != null) {
            if (reqParams.get(lastName).equals("DESC"))
                sorts.add(new Sort.Order(Sort.Direction.DESC, lastName));
            else
                sorts.add(new Sort.Order(Sort.Direction.ASC, lastName));
        }
        return sorts;
    }
}
