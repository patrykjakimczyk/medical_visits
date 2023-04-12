package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.dto.DoctorDTO;
import pl.medical.visits.dto.PatientDTO;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.service.WebService;

import java.util.Map;


@RestController("/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class WebController {

    private WebService webService;

    @PostMapping("registerPatient")
    public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        PatientDTO savedPatient = webService.registerPatient(requestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }

    @PostMapping("registerDoctor")
    public ResponseEntity<DoctorDTO> registerDoctor(@RequestBody DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        DoctorDTO savedDoctor = webService.registerDoctor(requestWrapper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);
    }

    @GetMapping("patients")
    public ResponseEntity<Page<PatientDTO>> getPatientsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatients(reqParams));
    }

    @GetMapping("doctors")
    public ResponseEntity<Page<DoctorDTO>> getDoctorsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctors(reqParams));
    }

    @GetMapping("doctorsPatients")
    public ResponseEntity<Page<PatientDTO>> getAllPatientsForDoctorId(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllPatientsForDoctorId(reqParams));
    }

    @GetMapping("patient/login")
    public ResponseEntity<AuthenticationResponse> loginPatient(@RequestBody UserLoginRequestWrapper userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.loginPatient(userLogin));
    }
}
