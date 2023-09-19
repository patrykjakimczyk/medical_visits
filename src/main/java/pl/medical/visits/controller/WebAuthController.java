package pl.medical.visits.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.model.dto.PatientDetailsDTO;
import pl.medical.visits.model.dto.VisitDTO;
import pl.medical.visits.model.response.GetDoctorVisitsInFutureResponse;
import pl.medical.visits.model.response.ResponseMessage;
import pl.medical.visits.model.request.*;
import pl.medical.visits.service.UserService;
import pl.medical.visits.service.VisitService;

import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public final class WebAuthController {
    private static final String GET_PATIENTS = "/auth/admin/all-patients";
    private static final String GET_DOCTORS = "/auth/admin/all-doctors";
    private static final String GET_DOCTORS_PATIENTS = "/auth/doctor/doctors-patients";
    private static final String GET_PATIENT_DATA = "/auth/patient/patient-data";
    private static final String UPDATE_PATIENT_DATA_FOR_PATIENT = "/auth/patient/update-patient";
    private static final String UPDATE_PATIENT_DATA = "/auth/doctor/update-patient";
    private static final String GET_DOCTOR_DATA = "/auth/doctor/doctor-data";
    private static final String UPDATE_DOCTOR_DATA = "/auth/doctor/update-doctor";
    private static final String REGISTER_VISIT = "/auth/patient/register-visit";
    private static final String GET_VISITS_FOR_ADMIN = "/auth/admin/visit";
    private static final String DOCTOR_VISIT = "/auth/doctor/visit";
    private static final String GET_VISITS_FOR_DOCTOR = "/auth/doctor/visits";
    private static final String GET_FUTURE_VISITS_TIMESTAMP_FOR_DOCTOR = "/auth/doctor/visits/timestamps";

    private final VisitService visitService;
    private final UserService userService;

    @GetMapping(GET_PATIENTS)
    public ResponseEntity<Page<PatientDTO>> getPatientsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPatients(reqParams));
    }

    @GetMapping(GET_DOCTORS)
    public ResponseEntity<Page<DoctorDTO>> getDoctorsWithPaging(@RequestParam Map<String, String> reqParams) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getDoctors(reqParams));
    }

    @GetMapping(GET_DOCTORS_PATIENTS)
    public ResponseEntity<Page<PatientDTO>> getAllPatientsForDoctorId(
            @RequestParam Map<String, String> reqParams,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllPatientsForDoctor(reqParams, auth.getName()));
    }

    @GetMapping(GET_PATIENT_DATA)
    public ResponseEntity<PatientDetailsDTO> getPatientsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPatientsFullData(auth.getName(), id));
    }

    @PatchMapping(UPDATE_PATIENT_DATA_FOR_PATIENT)
    public ResponseEntity<ResponseMessage> updatePatientDataForPatient(
            @RequestBody PatientEditDataForAdminRequest patientData,
            Authentication auth
    ) {
        userService.updatePatientDataForPatient(auth.getName(), patientData);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your data has been updated"));
    }

    @PatchMapping(UPDATE_PATIENT_DATA)
    public ResponseEntity<ResponseMessage> updatePatientData(
            @RequestBody PatientEditDataForAdminRequest patient,
            Authentication auth
    )  {
        userService.updatePatientData(auth.getName(), patient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Patient's data has been updated"));
    }

    @GetMapping(GET_DOCTOR_DATA)
    public ResponseEntity<DoctorDTO> getDoctorsDataForEdit(@RequestParam long id, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getDoctorsFullData(auth.getName(), id));
    }

    @PatchMapping(UPDATE_DOCTOR_DATA)
    public ResponseEntity<ResponseMessage> updateDataForDoctor(
            @RequestBody DoctorEditDataForAdminRequest doctor, Authentication auth
    ) {
        userService.updateDoctorData(auth.getName(), doctor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Doctor's data has been updated"));
    }

    @PostMapping(REGISTER_VISIT)
    public ResponseEntity<VisitDTO> registerVisit(
            @RequestBody RegisterVisitRequest visitWrapper,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitService.registerVisit(visitWrapper, auth.getName()));
    }

    @GetMapping(GET_VISITS_FOR_ADMIN)
    public ResponseEntity<Page<VisitDTO>> getAllVisits(@RequestParam Map<String, String> reqParams, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitService.getAllVisits(reqParams, auth.getName()));
    }

    @GetMapping(DOCTOR_VISIT)
    public ResponseEntity<VisitDTO> getVisitData(@RequestParam Long visitId, Authentication auth)  {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitService.getVisitData(visitId, auth.getName()));
    }

    @GetMapping(GET_VISITS_FOR_DOCTOR)
    public ResponseEntity<Page<VisitDTO>> getAllDoctorsVisits(@RequestParam Map<String, String> reqParams, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitService.getAllDoctorVisits(reqParams, auth.getName()));
    }

    @GetMapping(GET_FUTURE_VISITS_TIMESTAMP_FOR_DOCTOR)
    public ResponseEntity<GetDoctorVisitsInFutureResponse> getFutureDoctorVisitsTimestamps(@RequestParam Long doctorId) {
        GetDoctorVisitsInFutureResponse response = new GetDoctorVisitsInFutureResponse();
        response.setTimestamps(this.visitService.getFutureDoctorVisitsTimestamps(doctorId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(DOCTOR_VISIT)
    public ResponseEntity<VisitDTO> updateVisit(@RequestBody EditVisitRequest editVisit, Authentication auth) {
        return ResponseEntity.status(HttpStatus.OK).body(this.visitService.updateVisit(editVisit, auth.getName()));
    }
}
