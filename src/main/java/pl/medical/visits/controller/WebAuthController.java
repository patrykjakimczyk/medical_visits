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
import pl.medical.visits.model.request.*;
import pl.medical.visits.service.impl.WebServiceImpl;

import java.util.Map;

@RestController()
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public final class WebAuthController {
    private WebServiceImpl webServiceImpl;

    @GetMapping("/admin/patients")
    public ResponseEntity<Page<PatientDTO>> getPatientsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webServiceImpl.getPatients(reqParams));
    }

    @GetMapping("/admin/doctors")
    public ResponseEntity<Page<DoctorDTO>> getDoctorsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webServiceImpl.getDoctors(reqParams));
    }

    @GetMapping("/doctor/doctorsPatients")
    public ResponseEntity<Page<PatientDTO>> getAllPatientsForDoctorId(
            @RequestParam Map<String, String> reqParams,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(webServiceImpl.getAllPatientsForDoctor(reqParams, auth.getName()));
    }

    @GetMapping("/patient/patientData")
    public ResponseEntity<PatientDetailsDTO> getPatientsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webServiceImpl.getPatientsFullData(auth.getName(), id));
    }

    @PatchMapping("/patient/updatePatient")
    public ResponseEntity<ResponseMessage> updatePatientDataForPatient(@RequestBody PatientEditDataForAdminWrapper patientData, Authentication auth) throws NotUniqueValueException, ValidationException {
        webServiceImpl.updatePatientData(auth.getName(), patientData);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your data has been updated"));
    }

    @PatchMapping("/doctor/updatePatient")
    public ResponseEntity<ResponseMessage> updateDataForPatient(
            @RequestBody PatientEditDataForAdminWrapper patient, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webServiceImpl.updatePatientData(auth.getName(), patient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Patient's data has been updated"));
    }

    @GetMapping("/doctor/doctorData")
    public ResponseEntity<DoctorDTO> getDoctorsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webServiceImpl.getDoctorsFullData(auth.getName(), id));
    }

    @PatchMapping("/doctor/updateDoctor")
    public ResponseEntity<ResponseMessage> updateDataForDoctor(
            @RequestBody DoctorEditDataForAdminWrapper doctor, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webServiceImpl.updateDoctorData(auth.getName(), doctor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Doctor's data has been updated"));
    }

    @PostMapping("/patient/registerVisit")
    public ResponseEntity<VisitDTO> registerVisit(
            @RequestBody RegisterVisitWrapper visitWrapper,
            Authentication auth
    ) throws NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.registerVisit(visitWrapper, auth.getName()));
    }

    @GetMapping("/admin/visits")
    public ResponseEntity<Page<VisitDTO>> getAllVisits(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.getAllVisits(reqParams));
    }

    @GetMapping("/doctor/visit")
    public ResponseEntity<VisitDTO> getVisitData(@RequestParam Long visitId, Authentication auth)  {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.getVisitData(visitId, auth.getName()));
    }

    @GetMapping("/doctor/visits")
    public ResponseEntity<Page<VisitDTO>> getAllDoctorsVisits(@RequestParam Map<String, String> reqParams, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.getAllDoctorVisits(reqParams, auth.getName()));
    }

    @PatchMapping("/doctor/visit")
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody EditVisitWrapper editVisit, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webServiceImpl.updateVisit(editVisit, auth.getName()));
    }
}
