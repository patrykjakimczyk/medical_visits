package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Speciality;

@Getter
@ToString
public class SpecialityDTO {
    private final Long id;
    private final String specialityName;

    public SpecialityDTO(Speciality speciality) {
        this.id = speciality.getId();
        this.specialityName = speciality.getSpecialityName();
    }
}
