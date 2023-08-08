package pl.medical.visits.model.response;

import lombok.Data;
import org.springframework.data.domain.Page;
import pl.medical.visits.model.dto.DoctorWithoutPeselDTO;

@Data
public class GetDoctorsResponse {
    private Page<DoctorWithoutPeselDTO> doctors;
}
