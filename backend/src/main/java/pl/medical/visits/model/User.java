package pl.medical.visits.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String pesel;

    @Column
    private String birthDate;

    @Column(unique = true)
    private String phoneNr;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String apartmentNr;

    @Column
    private String postalCode;

    @Column
    private String email;

    @Column
    private String password;
}
