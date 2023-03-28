package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.user.*;
import pl.medical.visits.service.WebService;

import java.util.List;


@RestController("/")
@AllArgsConstructor
public class WebController {

    private WebService webService;

    @PostMapping("registerPatient")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        Patient savedPatient = webService.registerPatient(requestWrapper);
        return ResponseEntity.status(HttpStatus.OK).body(savedPatient);
    }

    @PostMapping("registerDoctor")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        Doctor savedDoctor = webService.registerDoctor(requestWrapper);
        return ResponseEntity.status(HttpStatus.OK).body(savedDoctor);
    }

    @GetMapping("allPatients")
    public ResponseEntity<List<User>> getAllPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllUsersByRole(Role.PATIENT));
    }

    @GetMapping("allDoctors")
    public ResponseEntity<List<User>> getAllDoctors() {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllUsersByRole(Role.DOCTOR));
    }

    @GetMapping("doctorsPatients")
    public ResponseEntity<List<User>> getAllPatientsForDoctorId(@RequestParam long id) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllPatientsForDoctorId(id));
    }
}
