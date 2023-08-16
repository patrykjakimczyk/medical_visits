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
import pl.medical.visits.service.WebService;

import java.util.Map;

@RestController()
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping()
public final class WebAuthController {
    private static final String GET_PATIENTS = "/auth/admin/all-patients";
    private static final String GET_DOCTORS = "/auth/admin/all-doctors";
    private static final String GET_DOCTORS_PATIENTS = "/auth/doctor/doctorsPatients";
    private static final String GET_PATIENT_DATA = "/auth/patient/patientData";
    private static final String UPDATE_PATIENT_DATA_FOR_PATIENT = "/auth/patient/updatePatient";
    private static final String UPDATE_PATIENT_DATA = "/auth/doctor/updatePatient";
    private static final String GET_DOCTOR_DATA = "/auth/doctor/doctorData";
    private static final String UPDATE_DOCTOR_DATA = "/auth/doctor/updateDoctor";
    private static final String REGISTER_VISIT = "/auth/patient/registerVisit";
    private static final String GET_VISITS_FOR_ADMIN = "/auth/admin/visit";
    private static final String DOCTOR_VISIT = "/auth/doctor/visit";
    private static final String GET_VISITS_FOR_DOCTOR = "/auth/doctor/visits";

    private final WebService webService;

    @GetMapping(GET_PATIENTS)
    public ResponseEntity<Page<PatientDTO>> getPatientsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatients(reqParams));
    }

    @GetMapping(GET_DOCTORS)
    public ResponseEntity<Page<DoctorDTO>> getDoctorsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctors(reqParams));
    }

    @GetMapping(GET_DOCTORS_PATIENTS)
    public ResponseEntity<Page<PatientDTO>> getAllPatientsForDoctorId(
            @RequestParam Map<String, String> reqParams,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getAllPatientsForDoctor(reqParams, auth.getName()));
    }

    @GetMapping(GET_PATIENT_DATA)
    public ResponseEntity<PatientDetailsDTO> getPatientsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getPatientsFullData(auth.getName(), id));
    }

    @PatchMapping(UPDATE_PATIENT_DATA_FOR_PATIENT)
    public ResponseEntity<ResponseMessage> updatePatientDataForPatient(@RequestBody PatientEditDataForAdminWrapper patientData, Authentication auth) throws NotUniqueValueException, ValidationException {
        webService.updatePatientData(auth.getName(), patientData);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your data has been updated"));
    }

    @PatchMapping(UPDATE_PATIENT_DATA)
    public ResponseEntity<ResponseMessage> updateDataForPatient(
            @RequestBody PatientEditDataForAdminWrapper patient, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webService.updatePatientData(auth.getName(), patient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Patient's data has been updated"));
    }

    @GetMapping(GET_DOCTOR_DATA)
    public ResponseEntity<DoctorDTO> getDoctorsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(webService.getDoctorsFullData(auth.getName(), id));
    }

    @PatchMapping(UPDATE_DOCTOR_DATA)
    public ResponseEntity<ResponseMessage> updateDataForDoctor(
            @RequestBody DoctorEditDataForAdminWrapper doctor, Authentication auth
    ) throws NotUniqueValueException, ValidationException {
        webService.updateDoctorData(auth.getName(), doctor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Doctor's data has been updated"));
    }

    @PostMapping(REGISTER_VISIT)
    public ResponseEntity<VisitDTO> registerVisit(
            @RequestBody RegisterVisitWrapper visitWrapper,
            Authentication auth
    ) throws NotUniqueValueException {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.registerVisit(visitWrapper, auth.getName()));
    }

    @GetMapping(GET_VISITS_FOR_ADMIN)
    public ResponseEntity<Page<VisitDTO>> getAllVisits(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getAllVisits(reqParams));
    }

    @GetMapping(DOCTOR_VISIT)
    public ResponseEntity<VisitDTO> getVisitData(@RequestParam Long visitId, Authentication auth)  {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getVisitData(visitId, auth.getName()));
    }

    @GetMapping(GET_VISITS_FOR_DOCTOR)
    public ResponseEntity<Page<VisitDTO>> getAllDoctorsVisits(@RequestParam Map<String, String> reqParams, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.getAllDoctorVisits(reqParams, auth.getName()));
    }

    @PatchMapping(DOCTOR_VISIT)
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody EditVisitWrapper editVisit, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.webService.updateVisit(editVisit, auth.getName()));
    }
}
