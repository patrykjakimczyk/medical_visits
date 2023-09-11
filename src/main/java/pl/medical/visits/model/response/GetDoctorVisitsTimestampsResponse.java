package pl.medical.visits.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class GetDoctorVisitsTimestampsResponse {
    private final List<Timestamp> timestamps;
}
