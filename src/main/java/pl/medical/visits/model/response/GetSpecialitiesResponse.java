package pl.medical.visits.model.response;

import lombok.Data;
import pl.medical.visits.model.dto.SpecialityDTO;

import java.util.List;

@Data
public class GetSpecialitiesResponse {
    private List<SpecialityDTO> specialities;
}
