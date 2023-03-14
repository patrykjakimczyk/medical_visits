package pl.medical.visits.model;

import com.sun.istack.NotNull;
import pl.medical.visits.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "user_table")
public abstract class User {
    @Id
    @Column(name = "user_id", insertable = false, updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "role", insertable = false, updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(length = 11, unique = true, nullable = false)
    private String pesel;

    @Column(name = "birth_date", length = 10, nullable = false)
    private String birthDate;

    @Column(name = "phone_nr", length = 30, unique = true, nullable = false)
    private String phoneNr;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String street;

    @Column(name = "house_nr", length = 3, nullable = false)
    private String houseNr;

    @Column(name = "apartment_nr", length = 3, nullable = false)
    private String apartmentNr;

    @Column(name = "postal_code", length = 6, nullable = false)
    private String postalCode;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;
}
