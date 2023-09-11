package pl.medical.visits.model.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class RegisterVisitRequest {
    private final Long doctorId;
    private final Timestamp timestamp;
    private final String description;
}
