package pl.medical.visits.model.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class DoctorEditDataForAdminWrapper {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String phoneNr;
    private final String email;
    private final List<Long> specialities;
}
