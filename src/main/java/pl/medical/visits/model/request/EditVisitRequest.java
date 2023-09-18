package pl.medical.visits.model.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class EditVisitRequest {
    private final Long id;
    private final Long officeId;
    private final Long patientId;
    private final Long doctorId;
    private final Timestamp timeStamp;
    private final String description;
}
