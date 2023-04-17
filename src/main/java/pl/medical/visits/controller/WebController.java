package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.service.WebService;


@RestController("/noAuth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class WebController {

    private WebService webService;

    @PostMapping("/registerPatient")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        PatientDTO savedPatient = webService.registerPatient(requestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        DoctorDTO savedDoctor = webService.registerDoctor(requestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequestWrapper userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.loginUser(userLogin));
    }
}
