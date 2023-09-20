package pl.medical.visits.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.UserDoesNotExistException;
import pl.medical.visits.exception.UserPerformedForbiddenActionException;
import pl.medical.visits.exception.WrongRequestParametersException;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.request.DoctorEditDataForAdminRequest;
import pl.medical.visits.model.request.PatientEditDataForAdminRequest;
import pl.medical.visits.model.request.PatientEditDataForPatientRequest;
import pl.medical.visits.repository.SpecialityRepository;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.repository.UserRepository;
import pl.medical.visits.service.UserService;
import pl.medical.visits.service.ValidationService;
import pl.medical.visits.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserAddressRepository userAddressRepository;
    private final ValidationService validationService;
    private final SpecialityRepository specialityRepository;

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
        User user;
        final String filterKey = reqParams.get("filterKey");
        final String filterType = reqParams.get("filterType");
        final PageRequest pageRequest = this.createPageRequest(reqParams);
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

    public PatientDetailsDTO getPatientsFullData(String tokenEmail, long patientId) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, patientId, Role.PATIENT)) {
            Optional<Patient> patientOptional =  userRepository.findPatientById(patientId);

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

    public DoctorDTO getDoctorsFullData(String tokenEmail, long doctorId) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, doctorId, Role.DOCTOR)) {
            Optional<Doctor> doctorOptional =  userRepository.findDoctorById(doctorId);

            if(doctorOptional.isPresent()) {
                return new DoctorDTO(
                        doctorOptional.get(),
                        userLoginRepository.findEmailForUserId(doctorId)
                );
            }
            throw new UserDoesNotExistException("User with given ID is not a doctor");
        }
        throw new UserPerformedForbiddenActionException("Doctors cannot access other doctors' data!");
    }

    public void updatePatientData(String tokenEmail, PatientEditDataForAdminRequest patientData) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, patientData.getId(), Role.PATIENT)
                && userRepository.existsById(patientData.getId())) {
            Optional<UserLoginData> loginDataOptional = userLoginRepository.findByUser(patientData.getId());
            UserLoginData loginData = loginDataOptional.orElseThrow(() -> new UserDoesNotExistException(
                    "Patient has not got a login data. His account was created incorrectly"
            ));
            Patient patient = (Patient) loginData.getUser();

            Optional<Doctor> doctorOptional = userRepository.findDoctorById(patientData.getAssignedDoctorId());
            doctorOptional.ifPresent(patient::setAssignedDoctor);

            this.updatePatient(patient, loginData, patientData);
            return;
        }
        throw new UserPerformedForbiddenActionException("Patient cannot access other users' data!");
    }

    public void updatePatientDataForPatient(String tokenEmail, PatientEditDataForPatientRequest patientData) {
        UserLoginData loginData = this.userLoginRepository.findByEmail(tokenEmail);
        Patient patient = (Patient) loginData.getUser();

        this.updatePatient(patient, loginData, patientData);
    }

    public void updateDoctorData(String tokenEmail, DoctorEditDataForAdminRequest doctorData) {
        if (this.canAuthUserAccessUserOfId(tokenEmail, doctorData.getId(), Role.DOCTOR)
                && userRepository.existsById(doctorData.getId())) {
            Optional<UserLoginData> loginDataOptional = userLoginRepository.findByUser(doctorData.getId());
            UserLoginData loginData = loginDataOptional.orElseThrow(() -> new UserDoesNotExistException(
                    "Doctor has not got a login data. His account was created incorrectly"
            ));
            Doctor doctor = (Doctor) loginData.getUser();

            doctor.setFirstName(StringUtil.firstCapital(doctorData.getFirstName()));
            doctor.setLastName(StringUtil.firstCapital(doctorData.getLastName()));
            doctor.setPhoneNr(doctorData.getPhoneNr());

            doctor.getSpecialities().clear();
            if (!doctorData.getSpecialities().isEmpty()) {
                doctorData.getSpecialities().forEach(id -> {
                    Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
                    optionalSpeciality.ifPresent(speciality -> doctor.getSpecialities().add(optionalSpeciality.get()));
                });
            }

            validationService.validateUser(doctor);

            loginData.setUser(doctor);
            loginData.setEmail(doctorData.getEmail());
            validationService.validateUserEmail(loginData);

            try {
                userRepository.save(doctor);
                userLoginRepository.save(loginData);
                return;
            } catch (RuntimeException e) {
                throw new NotUniqueValueException(
                        "Error has occurred during user's data update. e-mail/phone number isn't unique"
                );
            }
        }
        throw new UserPerformedForbiddenActionException("You are not allowed to access other users' data!");
    }

    private void updatePatient(Patient patient, UserLoginData loginData, PatientEditDataForPatientRequest patientData) {
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

    private User getUserByEmail(String email) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        return authenticatedUser.orElseThrow(
                () -> new UserDoesNotExistException("User from given token does not exist")
        );
    }

    private boolean canAuthUserAccessUserOfId(String email, long id, Role role) {
        User user = this.getUserByEmail(email);

        switch (role) {
            case PATIENT:
                return user.getRole().equals(role) ? user.getId() == id : true;
            case DOCTOR:
                return user.getRole().equals(role) ? user.getId() == id : user.getRole().equals(Role.ADMIN);
            case ADMIN:
                return user.getRole().equals(role) ? user.getId() == id : false;
            default:
                return false;
        }
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
