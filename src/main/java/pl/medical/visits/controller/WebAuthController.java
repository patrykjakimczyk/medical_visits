package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.service.WebService;

import java.util.Map;

@RestController("/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class WebAuthController {
    private WebService webService;

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
}
