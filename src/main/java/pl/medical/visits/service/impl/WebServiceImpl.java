package pl.medical.visits.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.medical.visits.config.JwtService;
import pl.medical.visits.exception.*;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.entity.Office;
import pl.medical.visits.model.entity.Visit;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.request.*;
import pl.medical.visits.repository.*;
import pl.medical.visits.service.ValidationService;
import pl.medical.visits.service.WebService;
import pl.medical.visits.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WebServiceImpl implements WebService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserAddressRepository userAddressRepository;
    private final VisitRepository visitRepository;
    private final OfficeRepository officeRepository;
    private final SpecialityRepository specialityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final ValidationService validationService;

    public AuthenticationResponse registerPatient(PatientRegistrationRequest requestWrapper)
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
        UserLoginRequest userLoginRequestWrapper = new UserLoginRequest(loginData.getEmail(), password);
        return loginUser(userLoginRequestWrapper);
    }

    public AuthenticationResponse registerDoctor(DoctorRegistrationRequest requestWrapper)
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
        UserLoginRequest userLoginRequestWrapper = new UserLoginRequest(loginData.getEmail(), password);
        return loginUser(userLoginRequestWrapper);
    }

    public Page<PatientDTO> getPatients(Map<String, String> reqParams) {
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);

        if (StringUtil.isStringNotBlank(filterKey) && StringUtil.isStringNotBlank(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findAllPatientsWithFirstNamePaging(filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findAllPatientsWithLastNamePaging(filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findAllPatientsWithPeselPaging(filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            }
        }
        return userRepository
                .findAllPatientsPaging(pageRequest)
                .map(patient -> {
                    String email = userLoginRepository.findEmailForUserId(patient.getId());
                    return new PatientDTO(patient, email);
                });
    }

    public Page<DoctorDTO> getDoctors(Map<String, String> reqParams) {
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);

        if (StringUtil.isStringNotBlank(filterKey) && StringUtil.isStringNotBlank(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findAllDoctorsWithFirstNamePaging(filterKey, pageRequest)
                        .map(doctor -> {
                            String email = userLoginRepository.findEmailForUserId(doctor.getId());
                            return new DoctorDTO(doctor, email);
                        });
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findAllDoctorsWithLastNamePaging(filterKey, pageRequest)
                        .map(doctor -> {
                            String email = userLoginRepository.findEmailForUserId(doctor.getId());
                            return new DoctorDTO(doctor, email);
                        });
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findAllDoctorsWithPeselPaging(filterKey, pageRequest)
                        .map(doctor -> {
                            String email = userLoginRepository.findEmailForUserId(doctor.getId());
                            return new DoctorDTO(doctor, email);
                        });
            }
        }
        return userRepository
                .findAllDoctorsPaging(pageRequest)
                .map(doctor -> {
                    String email = userLoginRepository.findEmailForUserId(doctor.getId());
                    return new DoctorDTO(doctor, email);
                });
    }

    public Page<DoctorWithoutPeselDTO> getDoctorsBySpeciality(long specialityId, Map<String, String> reqParams) {
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);

        if (StringUtil.isStringNotBlank(filterKey) && StringUtil.isStringNotBlank(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findDoctorsWithSpecialityAndFirstName(specialityId, filterKey, pageRequest)
                        .map(doctor -> {
                            String email = userLoginRepository.findEmailForUserId(doctor.getId());
                            return new DoctorWithoutPeselDTO(doctor, email);
                        });
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findDoctorsWithSpecialityAndLastName(specialityId, filterKey, pageRequest)
                        .map(doctor -> {
                            String email = userLoginRepository.findEmailForUserId(doctor.getId());
                            return new DoctorWithoutPeselDTO(doctor, email);
                        });
            }
        }
        return userRepository
                .findDoctorsWithSpeciality(specialityId, pageRequest)
                .map(doctor -> {
                    String email = userLoginRepository.findEmailForUserId(doctor.getId());
                    return new DoctorWithoutPeselDTO(doctor, email);
                });
    }

    public Page<PatientDTO> getAllPatientsForDoctor(Map<String, String> reqParams, String tokenEmail) {
        int offset;
        int pageSize;
        User user;
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(tokenEmail);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isPresent()) {
            user = authenticatedUser.get();

            if (!user.getRole().equals(Role.DOCTOR))  {
                throw new UserPerformedForbiddenActionException("Only doctor can access his assigned patients!");
            }
        } else {
            throw new UserDoesNotExistException("User from given token does not exist");
        }

        long id = user.getId();

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Invalid request parameters (no paging information or ID)");
        }

        List<Sort.Order> sorts = findSorts(reqParams);
        PageRequest pageRequest;

        if (sorts.isEmpty()) pageRequest = PageRequest.of(offset, pageSize);
        else pageRequest = PageRequest.of(offset, pageSize, Sort.by(sorts));

        if (StringUtil.isStringNotBlank(filterKey) && StringUtil.isStringNotBlank(filterType)) {
            if (filterType.equals("firstName")) {
                return userRepository
                        .findPatientsWithFirstNameForDoctor(id, filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            } else if (filterType.equals("lastName")) {
                return userRepository
                        .findPatientsWithLastNameForDoctor(id, filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            }  else if (filterType.equals("pesel")) {
                return userRepository
                        .findPatientsWithPeselForDoctor(id, filterKey, pageRequest)
                        .map(patient -> {
                            String email = userLoginRepository.findEmailForUserId(patient.getId());
                            return new PatientDTO(patient, email);
                        });
            }
        }
        return userRepository
                .findPatientsForDoctor(id, pageRequest)
                .map(patient -> {
                    String email = userLoginRepository.findEmailForUserId(patient.getId());
                    return new PatientDTO(patient, email);
                });
    }

    public List<SpecialityDTO> getSpecialities() {
        return this.specialityRepository
                .findAll()
                .stream()
                .map(SpecialityDTO::new)
                .collect(Collectors.toList());
    }

    public PatientDetailsDTO getPatientsFullData(String tokenEmail, long id) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, id, Role.PATIENT)) {
            Optional<Patient> patientOptional =  userRepository.findPatientById(id);

            Patient patient = patientOptional.orElseThrow(() ->
                    new UserDoesNotExistException("User with given ID is not a patient"));
            String email = userLoginRepository.findEmailForUserId(patient.getId());

            return new PatientDetailsDTO(
                    patient,
                    new UserAddressDTO(patient.getAddressData()),
                    email
            );
        }
        throw new UserPerformedForbiddenActionException("Patient cannot access other users' data!");
    }

    public DoctorDTO getDoctorsFullData(String tokenEmail, long id) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, id, Role.DOCTOR)) {
            Optional<Doctor> doctorOptional =  userRepository.findDoctorById(id);

            if(doctorOptional.isPresent()) {
                return new DoctorDTO(
                        doctorOptional.get(),
                        userLoginRepository.findEmailForUserId(id)
                );
            }
            throw new UserDoesNotExistException("User with given ID is not a doctor");
        }
        throw new UserPerformedForbiddenActionException("Doctors cannot access other doctors' data!");
    }

    public void updatePatientData(String tokenEmail, PatientEditDataForAdminWrapper patientData)
            throws ValidationException, NotUniqueValueException {
        if (this.canAuthUserAccessUserOfId(tokenEmail, patientData.getId(), Role.PATIENT)
                && userRepository.existsById(patientData.getId())) {
            Optional<UserLoginData> loginDataOptional = userLoginRepository.findByUser(patientData.getId());

            if (loginDataOptional.isEmpty()) {
                throw new UserDoesNotExistException(
                        "Patient has not got a login data. His account was created incorrectly"
                );
            }
            UserLoginData loginData = loginDataOptional.get();
            Patient patient = (Patient) loginData.getUser();

            patient.setFirstName(StringUtil.firstCapital(patientData.getFirstName()));
            patient.setLastName(StringUtil.firstCapital(patientData.getLastName()));
            patient.setPhoneNr(patientData.getPhoneNr());

            Optional<Doctor> doctorOptional = userRepository.findDoctorById(patientData.getAssignedDoctorId());
            doctorOptional.ifPresent(patient::setAssignedDoctor);

            validationService.validateUser(patient);

            loginData.setEmail(patientData.getEmail());
            loginData.setUser(patient);
            validationService.validateUserEmail(loginData);

            UserAddressData addressData = patient.getAddressData();
            addressData.setUser(patient);
            addressData.setCountry(patientData.getCountry());
            addressData.setCity(patientData.getCity());
            addressData.setStreet(patientData.getStreet());
            addressData.setHouseNr(patientData.getHouseNr());
            addressData.setApartmentNr(patientData.getApartmentNr());
            addressData.setPostalCode(patientData.getPostalCode());
            validationService.validateUserAddress(addressData);

            try {
                userAddressRepository.save(addressData);
                userLoginRepository.save(loginData);
            } catch (RuntimeException e) {
                throw new NotUniqueValueException(
                        "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
                );
            }
            return;
        }
        throw new UserPerformedForbiddenActionException("Patient cannot access other users' data!");
    }

    public void updatePatientDataForPatient(String tokenEmail, PatientEditDataForPatientWrapper patientData)
            throws ValidationException, NotUniqueValueException {
        UserLoginData loginData = this.userLoginRepository.findByEmail(tokenEmail);
        Patient patient = (Patient) loginData.getUser();

        patient.setFirstName(StringUtil.firstCapital(patientData.getFirstName()));
        patient.setLastName(StringUtil.firstCapital(patientData.getLastName()));
        patient.setPhoneNr(patientData.getPhoneNr());

        validationService.validateUser(patient);

        loginData.setEmail(patientData.getEmail());
        loginData.setUser(patient);
        validationService.validateUserEmail(loginData);

        UserAddressData addressData = patient.getAddressData();
        addressData.setUser(patient);
        addressData.setCountry(patientData.getCountry());
        addressData.setCity(patientData.getCity());
        addressData.setStreet(patientData.getStreet());
        addressData.setHouseNr(patientData.getHouseNr());
        addressData.setApartmentNr(patientData.getApartmentNr());
        addressData.setPostalCode(patientData.getPostalCode());
        validationService.validateUserAddress(addressData);

        try {
            userAddressRepository.save(addressData);
            userLoginRepository.save(loginData);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
    }

    public void updateDoctorData(String tokenEmail, DoctorEditDataForAdminWrapper doctorData)
            throws ValidationException, NotUniqueValueException {
        if (this.canAuthUserAccessUserOfId(tokenEmail, doctorData.getId(), Role.DOCTOR)
                && userRepository.existsById(doctorData.getId())) {
            Optional<UserLoginData> loginDataOptional = userLoginRepository.findByUser(doctorData.getId());

            if (loginDataOptional.isEmpty()) {
                throw new UserDoesNotExistException(
                        "Doctor has not got a login data. His account was created incorrectly"
                );
            }

            UserLoginData loginData = loginDataOptional.get();
            Doctor doctor = (Doctor)loginData.getUser();

            doctor.setFirstName(StringUtil.firstCapital(doctorData.getFirstName()));
            doctor.setLastName(StringUtil.firstCapital(doctorData.getLastName()));

            if (!doctorData.getSpecialities().isEmpty() || doctorData.getSpecialities() != null) {
                doctor.getSpecialities().clear();
                for (long id : doctorData.getSpecialities()) {
                    Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
                    optionalSpeciality.ifPresent(speciality -> doctor.getSpecialities().add(optionalSpeciality.get()));
                }
            }

            if (!doctor.getPhoneNr().equals(doctorData.getPhoneNr())) {
                doctor.setPhoneNr(doctorData.getPhoneNr());
            }
            validationService.validateUser(doctor);

            loginData.setUser(doctor);
            if (!loginData.getEmail().equals(doctorData.getEmail())) {
                loginData.setEmail(doctorData.getEmail());
            }
            validationService.validateUserEmail(loginData);

            try {
                userRepository.save(doctor);
                userLoginRepository.save(loginData);
            } catch (RuntimeException e) {
                throw new NotUniqueValueException(
                        "Error has occurred during user's data update. e-mail/phone number isn't unique"
                );
            }
            return;
        }
        throw new UserPerformedForbiddenActionException("You are not allowed to access other users' data!");
    }

    public AuthenticationResponse loginUser(UserLoginRequest userLogin) {
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

    public VisitDTO registerVisit(RegisterVisitWrapper visitWrapper, String authUserEmail) throws NotUniqueValueException {
//        if (this.canAuthUserAccessUserOfId(authUserEmail, visitWrapper.getPatientId(), Role.PATIENT)) {

            UserLoginData loginData = this.userLoginRepository.findByEmail(authUserEmail);
            Optional<Doctor> doctorOptional = this.userRepository.findDoctorById(visitWrapper.getDoctorId());

            if (!loginData.getUser().getRole().equals(Role.PATIENT)) {
                throw new UserPerformedForbiddenActionException("Only patient can register visit!");
            }

            Patient patient = (Patient) loginData.getUser();

            if (doctorOptional.isEmpty()) {
                throw new UserDoesNotExistException(
                        "Cannot register visit because patient or doctor with given id does not exist!"
                );
            }

            Doctor doctor = doctorOptional.get();

            Visit visit = new Visit();
            visit.setPatient(patient);
            visit.setDoctor(doctor);
            visit.setDescription(visitWrapper.getDescription());
            visit.setTimeStamp(visitWrapper.getTimestamp());

            Optional<Office> officeOptional = officeRepository.findByDoctor(doctor);
            visit.setOffice(
                    officeOptional.orElseThrow(() ->
                            new DoctorDoesNotHaveAssignedOfficeException("Doctor does not have assigned office!"))
            );

            try {
                visitRepository.save(visit);
            } catch (RuntimeException e) {
                throw new NotUniqueValueException("Cannot register visit for occupied date");
            }

            return new VisitDTO(visit,  authUserEmail);
//        }
//        throw new UserPerformedForbiddenActionException("You are not allowed to register visit for another patient");
    }

    public Page<VisitDTO> getAllVisits(Map<String, String> reqParams) {
        final PageRequest pageRequest = this.createPageRequest(reqParams).withSort(Sort.Direction.DESC, "timeStamp");
        return visitRepository.findAll(pageRequest)
                .map(visit -> new VisitDTO(visit, null));
    }

    public VisitDTO getVisitData(Long visitId, String email) {
        Optional<Visit> visitOptional = visitRepository.findById(visitId);

        if (visitOptional.isEmpty()) {
            throw new UserPerformedForbiddenActionException("Visit with given id does not exist");
        }

        Visit visit = visitOptional.get();
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isEmpty()) {
            throw new UserDoesNotExistException("User from given token does not exist");
        }

        User user = authenticatedUser.get();

        if (visit.getDoctor().getId() != user.getId()) {
            if (user.getRole().equals(Role.ADMIN)) {
                return new VisitDTO(visit, null);
            }
            throw new UserPerformedForbiddenActionException("You cannot access this visit's data");
        }
        return new VisitDTO(visit, null);
    }

    public Page<VisitDTO> getAllDoctorVisits(Map<String, String> reqParams, String email) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isEmpty()) {
            throw new UserDoesNotExistException("User from given token does not exist");
        }

        User user = authenticatedUser.get();

        if (!user.getRole().equals(Role.DOCTOR)) {
            throw new UserPerformedForbiddenActionException("You cannot access those visits' data");
        }

        final PageRequest pageRequest = this.createPageRequest(reqParams).withSort(Sort.Direction.DESC, "time_stamp");
        return visitRepository.findAllDoctorVisits(user.getId(), pageRequest)
                .map(visit -> new VisitDTO(visit, null));
    }

    public VisitDTO updateVisit(EditVisitWrapper givenVisit, String email) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isEmpty()) {
            throw new UserDoesNotExistException("User from given token does not exist");
        }

        User user = authenticatedUser.get();

        Optional<Visit> visitOptional = visitRepository.findById(givenVisit.getId());

        if(visitOptional.isEmpty()) {
            throw new UserPerformedForbiddenActionException("Cannot update visit which does not exist");
        }
        Visit visit = visitOptional.get();

        if (givenVisit.getDoctorId() != user.getId()
                && !user.getRole().equals(Role.ADMIN)
                && visit.getDoctor().getId() == givenVisit.getDoctorId()
        ) {
            throw new UserPerformedForbiddenActionException("You cannot access those visits' data");
        }

        visit.setDescription(givenVisit.getDescription());
        visit.setTimeStamp(givenVisit.getTimeStamp());

        visitRepository.save(visit);
        return new VisitDTO(visit, null);
    }

    private boolean canAuthUserAccessUserOfId(String email, long id, Role role) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isPresent()) {
            User user = authenticatedUser.get();

            if (user.getRole().equals(role)) {
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
