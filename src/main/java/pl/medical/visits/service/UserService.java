package pl.medical.visits.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.medical.visits.model.dto.DoctorDTO;
import pl.medical.visits.model.dto.DoctorWithoutPeselDTO;
import pl.medical.visits.model.dto.PatientDTO;
import pl.medical.visits.model.dto.PatientDetailsDTO;
import pl.medical.visits.model.request.DoctorEditDataForAdminWrapper;
import pl.medical.visits.model.request.PatientEditDataForAdminRequest;
import pl.medical.visits.model.request.PatientEditDataForPatientRequest;

import java.util.Map;

@Service
public interface UserService {
    Page<PatientDTO> getPatients(Map<String, String> reqParams);
    Page<DoctorDTO> getDoctors(Map<String, String> reqParams);
    Page<DoctorWithoutPeselDTO> getDoctorsBySpeciality(long specialityId, Map<String, String> reqParams);
    Page<PatientDTO> getAllPatientsForDoctor(Map<String, String> reqParams, String tokenEmail);
    PatientDetailsDTO getPatientsFullData(String tokenEmail, long patientId);
    DoctorDTO getDoctorsFullData(String tokenEmail, long doctorId);
    void updatePatientData(String tokenEmail, PatientEditDataForAdminRequest patientData);
    void updatePatientDataForPatient(String tokenEmail, PatientEditDataForPatientRequest patientData);
    void updateDoctorData(String tokenEmail, DoctorEditDataForAdminWrapper doctorData);
}
