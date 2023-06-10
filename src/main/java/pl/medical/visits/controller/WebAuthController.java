package pl.medical.visits.controller;

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
import pl.medical.visits.model.dto.VisitDTO;
import pl.medical.visits.model.response.ResponseMessage;
import pl.medical.visits.model.wrapper.*;
import pl.medical.visits.service.WebService;

import java.util.List;
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
            @RequestParam Map<String, String> reqParams,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllPatientsForDoctor(reqParams, auth.getName()));
    }

    @GetMapping("/patient/patientData")
    public ResponseEntity<PatientDetailsDTO> getPatientsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatientsFullData(auth.getName(), id));
    }

    @PatchMapping("/patient/updatePatient")
    public ResponseEntity<ResponseMessage> updatePatientDataForPatient(@RequestBody PatientEditDataForPatientWrapper patientData, Authentication auth) throws NotUniqueValueException, ValidationException {
        webService.updatePatientDataForPatient(auth.getName(), patientData);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your data has been updated"));
    }

    @PatchMapping("/doctor/updatePatient")
    public ResponseEntity<ResponseMessage> updateDataForPatient(
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

    @PostMapping("/patient/registerVisit")
    public ResponseEntity<VisitDTO> registerVisit(
            @RequestBody RegisterVisitWrapper visitWrapper,
            Authentication auth
    ) throws NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.registerVisit(visitWrapper, auth.getName()));
    }

    @GetMapping("/admin/visits")
    public ResponseEntity<List<VisitDTO>> getAllVisits() {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getAllVisits());
    }

    @GetMapping("/doctor/visit")
    public ResponseEntity<VisitDTO> getVisitData(@RequestParam Long visitId, Authentication auth)  {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getVisitData(visitId, auth.getName()));
    }

    @GetMapping("/doctor/visits")
    public ResponseEntity<List<VisitDTO>> getAllDoctorsVisits(@RequestParam Long doctorId, @RequestParam Map<String, String> reqParams, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getAllDoctorVisits(doctorId, reqParams, auth.getName()));
    }

    @PatchMapping("/doctor/visit")
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody EditVisitWrapper editVisit, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.updateVisit(editVisit, auth.getName()));
    }
}
