package pl.medical.visits.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.entity.user.Speciality;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.model.request.*;

import java.util.List;
import java.util.Map;

@Service
public interface WebService {
    Page<PatientDTO> getPatients(Map<String, String> reqParams);

    Page<DoctorDTO> getDoctors(Map<String, String> reqParams);

    Page<DoctorWithoutPeselDTO> getDoctorsBySpeciality(long specialityId, Map<String, String> reqParams);

    Page<PatientDTO> getAllPatientsForDoctor(Map<String, String> reqParams, String tokenEmail);

    List<SpecialityDTO> getSpecialities();

    PatientDetailsDTO getPatientsFullData(String tokenEmail, long id);

    DoctorDTO getDoctorsFullData(String tokenEmail, long id);

    void updatePatientData(String tokenEmail, PatientEditDataForAdminWrapper patientData)
            throws ValidationException, NotUniqueValueException;

    void updatePatientDataForPatient(String tokenEmail, PatientEditDataForPatientWrapper patientData)
            throws ValidationException, NotUniqueValueException;

    void updateDoctorData(String tokenEmail, DoctorEditDataForAdminWrapper doctorData)
            throws ValidationException, NotUniqueValueException;

    VisitDTO registerVisit(RegisterVisitWrapper visitWrapper, String authUserEmail)
            throws NotUniqueValueException;

    Page<VisitDTO> getAllVisits(Map<String, String> reqParams);

    VisitDTO getVisitData(Long visitId, String email);

    Page<VisitDTO> getAllDoctorVisits(Map<String, String> reqParams, String email);

    VisitDTO updateVisit(EditVisitWrapper givenVisit, String email);
}
