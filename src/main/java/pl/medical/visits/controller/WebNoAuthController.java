package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.request.DoctorRegistrationRequest;
import pl.medical.visits.model.request.PatientRegistrationRequest;
import pl.medical.visits.model.request.UserLoginRequest;
import pl.medical.visits.model.response.GetDoctorsResponse;
import pl.medical.visits.model.response.GetSpecialitiesResponse;
import pl.medical.visits.service.RegistrationService;
import pl.medical.visits.service.UserService;
import pl.medical.visits.service.VisitService;

import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public final class WebNoAuthController {
    private static final String REGISTER_PATIENT = "/patient/register";
    private static final String REGISTER_DOCTOR = "/doctor/register";
    private static final String LOGIN = "/login";
    private static final String GET_SPECIALITIES = "/speciality/all-specialities";
    private static final String GET_DOCTORS = "/doctor/all-doctors";

    private final VisitService webService;
    private final UserService userService;
    private final RegistrationService registrationService;

    @PostMapping(REGISTER_PATIENT)
    public ResponseEntity<AuthenticationResponse> registerPatient(@RequestBody PatientRegistrationRequest requestWrapper)
            throws ValidationException, NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.registrationService.registerPatient(requestWrapper));
    }

    @PostMapping(REGISTER_DOCTOR)
    public ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody DoctorRegistrationRequest requestWrapper)
            throws NotUniqueValueException, ValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.registrationService.registerDoctor(requestWrapper));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequest userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(this.registrationService.loginUser(userLogin));
    }

    @GetMapping(GET_SPECIALITIES)
    public ResponseEntity<GetSpecialitiesResponse> getSpecialities() {
        GetSpecialitiesResponse response = new GetSpecialitiesResponse();
        response.setSpecialities(this.webService.getSpecialities());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(GET_DOCTORS)
    public ResponseEntity<GetDoctorsResponse> getDoctorsWithPaging(
            @RequestParam Long specialityId,
            @RequestParam Map<String, String> reqParams
    ) {
        GetDoctorsResponse response = new GetDoctorsResponse();
        response.setDoctors(this.userService.getDoctorsBySpeciality(specialityId, reqParams));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
