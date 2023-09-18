package pl.medical.visits.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.Visit;

import java.sql.Timestamp;

@Getter
@ToString
public class VisitDTO {
    private final Long id;
    private final OfficeDTO office;
    private final PatientDTO patient;
    private final AssignedDoctorDTO doctor;
    private final Timestamp timeStamp;
    private final String description;

    public VisitDTO(Visit visit, String patientEmail) {
        this.id = visit.getId();
        this.office = new OfficeDTO(visit.getOffice());
        this.patient = new PatientDTO(visit.getPatient(), patientEmail);
        this.doctor = new AssignedDoctorDTO(visit.getDoctor());
        this.timeStamp = visit.getTimeStamp();
        this.description = visit.getDescription();
    }

    public VisitDTO() {
        this.id = null;
        this.office = null;
        this.patient = null;
        this.doctor = null;
        this.timeStamp = null;
        this.description = null;
    }
}
