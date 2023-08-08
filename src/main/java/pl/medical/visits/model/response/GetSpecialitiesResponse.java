package pl.medical.visits.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.medical.visits.model.entity.user.Speciality;

import java.util.List;

@Data
public class GetSpecialitiesResponse {
    private List<Speciality> specialities;
}
