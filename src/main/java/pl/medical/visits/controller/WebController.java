package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.model.dto.DoctorWithoutPeselDTO;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.request.DoctorRegistrationRequest;
import pl.medical.visits.model.request.PatientRegistrationRequest;
import pl.medical.visits.model.request.UserLoginRequest;
import pl.medical.visits.model.response.GetDoctorsResponse;
import pl.medical.visits.model.response.GetSpecialitiesResponse;
import pl.medical.visits.service.impl.WebServiceImpl;

import java.util.List;
import java.util.Map;


@RestController()
@AllArgsConstructor
@CrossOrigin(origins = "*")
public final class WebController {

    private WebServiceImpl webServiceImpl;

    @PostMapping("/registerPatient")
    public ResponseEntity<AuthenticationResponse> registerPatient(@RequestBody PatientRegistrationRequest requestWrapper)
            throws ValidationException, NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.webServiceImpl.registerPatient(requestWrapper));
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody DoctorRegistrationRequest requestWrapper)
            throws NotUniqueValueException, ValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.webServiceImpl.registerDoctor(requestWrapper));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequest userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.loginUser(userLogin));
    }

    @GetMapping("/getSpecialities")
    public ResponseEntity<GetSpecialitiesResponse> getSpecialities() {
        GetSpecialitiesResponse response = new GetSpecialitiesResponse();
        response.setSpecialities(this.webServiceImpl.getSpecialities());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/doctors")
    public ResponseEntity<GetDoctorsResponse> getDoctorsWithPaging(
            @RequestParam Long specialityId,
            @RequestParam Map<String, String> reqParams
    ) {
        GetDoctorsResponse response = new GetDoctorsResponse();
        response.setDoctors(this.webServiceImpl.getDoctorsBySpeciality(specialityId, reqParams));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
