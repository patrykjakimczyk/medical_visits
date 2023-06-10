package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.DoctorWithoutPeselDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.wrapper.DoctorRequestWrapper;
import pl.medical.visits.model.wrapper.PatientRequestWrapper;
import pl.medical.visits.model.wrapper.UserLoginRequestWrapper;
import pl.medical.visits.service.WebService;

import java.util.List;
import java.util.Map;


@RestController("/noAuth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class WebController {

    private WebService webService;

    @PostMapping("/registerPatient")
    public ResponseEntity<AuthenticationResponse> registerPatient(@RequestBody PatientRequestWrapper requestWrapper)
            throws ValidationException, NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.CREATED).body(webService.registerPatient(requestWrapper));
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<AuthenticationResponse> registerDoctor(@RequestBody DoctorRequestWrapper requestWrapper)
            throws NotUniqueValueException, ValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(webService.registerDoctor(requestWrapper));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserLoginRequestWrapper userLogin) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.loginUser(userLogin));
    }

    @GetMapping("/getSpecialities")
    public ResponseEntity<List<Speciality>> getSpecialities() {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getSpecialities());
    }

    @GetMapping("/doctors")
    public ResponseEntity<Page<DoctorWithoutPeselDTO>> getDoctorsWithPaging(
            @RequestParam Long specialityId,
            @RequestParam Map<String, String> reqParams
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctorsBySpeciality(specialityId, reqParams));
    }
}
