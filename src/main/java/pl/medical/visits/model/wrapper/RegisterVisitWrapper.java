package pl.medical.visits.model.wrapper;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RegisterVisitWrapper {
    private final Long doctorId;
    private final Timestamp timestamp;
    private final String description;
}
