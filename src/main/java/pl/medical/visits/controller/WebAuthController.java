package pl.medical.visits.controller;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.model.dto.PatientDetailsDTO;
import pl.medical.visits.model.response.ResponseMessage;
import pl.medical.visits.model.wrapper.DoctorEditDataForAdminWrapper;
import pl.medical.visits.model.wrapper.PatientEditDataForAdminWrapper;
import pl.medical.visits.service.WebService;

import java.util.Map;

@RestController("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class WebAuthController {
    private WebService webService;

    @GetMapping("/admin/patients")
    public ResponseEntity<Page<PatientDTO>> getPatientsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatients(reqParams));
    }

    @GetMapping("/admin/doctors")
    public ResponseEntity<Page<DoctorDTO>> getDoctorsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctors(reqParams));
    }

    @GetMapping("/doctor/doctorsPatients")
    public ResponseEntity<Page<PatientDTO>> getAllPatientsForDoctorId(
            @NotNull @RequestParam Map<String, String> reqParams,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllPatientsForDoctor(reqParams, auth.getName()));
    }

    @GetMapping("/patient/patientData")
    public ResponseEntity<PatientDetailsDTO> getPatientsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatientsFullData(auth.getName(), id));
    }

    @PatchMapping("/patient/updatePatient")
    public ResponseEntity<ResponseMessage> updateDataForUser(
            @RequestBody PatientEditDataForAdminWrapper patient, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webService.updatePatientData(auth.getName(), patient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Patient's data has been updated"));
    }

    @GetMapping("/doctor/doctorData")
    public ResponseEntity<DoctorDTO> getDoctorsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctorsFullData(auth.getName(), id));
    }

    @PatchMapping("/doctor/updateDoctor")
    public ResponseEntity<ResponseMessage> updateDataForDoctor(
            @RequestBody DoctorEditDataForAdminWrapper doctor, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webService.updateDoctorData(auth.getName(), doctor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Doctor's data has been updated"));
    }
}
