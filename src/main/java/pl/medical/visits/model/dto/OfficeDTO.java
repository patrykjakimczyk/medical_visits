package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.Office;

@Getter
@ToString
public class OfficeDTO {
    private final Long id;
    private final String officeNr;
    private final String floor;

    public OfficeDTO(Office office) {
        this.id = office.getId();
        this.officeNr = office.getOfficeNr();
        this.floor = office.getFloor();
    }
}
