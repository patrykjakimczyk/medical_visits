package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.UserAddressData;

@Getter
@ToString
public class UserAddressDTO {
    private final long id;
    private final String country;
    private final String city;
    private final String street;
    private final String houseNr;
    private final String apartmentNr;
    private final String postalCode;

    public UserAddressDTO(UserAddressData addressData) {
        this.id = addressData.getId();
        this.country = addressData.getCountry();
        this.city = addressData.getCity();
        this.street = addressData.getStreet();
        this.houseNr = addressData.getHouseNr();
        this.apartmentNr = addressData.getApartmentNr();
        this.postalCode = addressData.getPostalCode();
    }
}
