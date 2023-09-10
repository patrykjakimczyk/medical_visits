package pl.medical.visits.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.request.*;

import java.util.List;
import java.util.Map;

@Service
public interface WebService {
    List<SpecialityDTO> getSpecialities();
    VisitDTO registerVisit(RegisterVisitWrapper visitWrapper, String authUserEmail);
    Page<VisitDTO> getAllVisits(Map<String, String> reqParams);
    VisitDTO getVisitData(Long visitId, String email);
    Page<VisitDTO> getAllDoctorVisits(Map<String, String> reqParams, String email);
    VisitDTO updateVisit(EditVisitWrapper givenVisit, String email);
}
