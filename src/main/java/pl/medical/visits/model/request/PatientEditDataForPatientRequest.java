package pl.medical.visits.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientEditDataForPatientRequest {
    public long id;
    public String firstName;
    public String lastName;
    public String phoneNr;
    public String email;
    public String country;
    public String city;
    public String street;
    public String houseNr;
    public String apartmentNr;
    public String postalCode;
}
