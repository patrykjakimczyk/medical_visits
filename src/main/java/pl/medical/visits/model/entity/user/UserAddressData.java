package pl.medical.visits.model.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_address")
public class UserAddressData {
    @Id
    @SequenceGenerator(
            name = "user_add_seq",
            sequenceName = "user_add_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_add_seq")
    @Column(name = "address_id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String street;

    @Column(name = "house_nr", length = 4, nullable = false)
    private String houseNr;

    @Column(name = "apartment_nr", length = 4)
    private String apartmentNr;

    @Column(name = "postal_code", length = 6, nullable = false)
    private String postalCode;
}
