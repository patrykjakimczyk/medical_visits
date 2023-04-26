package pl.medical.visits.model.entity.user;

import pl.medical.visits.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "user_t")
public abstract class User {
    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id", insertable = false, updatable = false, unique = true, nullable = false)
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

    @Column(name = "sex", length = 6, nullable = false)
    private String gender;

    @Column(name = "phone_nr", length = 11, unique = true, nullable = false)
    private String phoneNr;

    public User() {
        super();
        this.setRole(Role.ADMIN);
    }
}
