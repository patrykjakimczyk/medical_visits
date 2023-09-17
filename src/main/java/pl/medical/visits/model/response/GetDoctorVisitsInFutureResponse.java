package pl.medical.visits.model.response;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class GetDoctorVisitsInFutureResponse {
    private List<Timestamp> timestamps;
}
