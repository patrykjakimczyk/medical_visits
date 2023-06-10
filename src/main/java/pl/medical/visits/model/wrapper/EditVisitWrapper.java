package pl.medical.visits.model.wrapper;

import lombok.Data;
import pl.medical.visits.model.dto.AssignedDoctorDTO;
import pl.medical.visits.model.dto.OfficeDTO;
import pl.medical.visits.model.dto.PatientDTO;

import java.sql.Timestamp;

@Data
public class EditVisitWrapper {
    private final Long id;
    private final Long officeId;
    private final Long patientId;
    private final Long doctorId;
    private final Timestamp timeStamp;
    private final String description;
}
