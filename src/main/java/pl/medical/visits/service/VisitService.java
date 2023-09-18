package pl.medical.visits.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.request.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public interface VisitService {
    List<SpecialityDTO> getSpecialities();
    VisitDTO registerVisit(RegisterVisitRequest visitWrapper, String email);
    VisitDTO updateVisit(EditVisitRequest givenVisit, String email);
    Page<VisitDTO> getAllVisits(Map<String, String> reqParams, String email);
    VisitDTO getVisitData(Long visitId, String email);
    Page<VisitDTO> getAllDoctorVisits(Map<String, String> reqParams, String email);
    List<Timestamp> getFutureDoctorVisitsTimestamps(Long doctorId);
}
