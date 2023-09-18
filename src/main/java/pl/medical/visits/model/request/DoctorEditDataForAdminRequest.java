package pl.medical.visits.model.request;

import lombok.Data;

import java.util.List;

@Data
public final class DoctorEditDataForAdminRequest {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String phoneNr;
    private final String email;
    private final List<Long> specialities;
}
