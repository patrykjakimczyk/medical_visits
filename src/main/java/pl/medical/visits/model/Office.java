package pl.medical.visits.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medical.visits.model.user.Doctor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    @Id
    @SequenceGenerator(
            name = "office_seq",
            sequenceName = "office_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_seq")
    @Column(name = "office_id", insertable = false, updatable = false, unique = true, nullable = false)
    private long id;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "user_id", unique = true, nullable = false)
    private Doctor doctor;

    @Column(name = "office_nr", length = 3, unique = true, nullable = false)
    private String officeNr;

    @Column(length = 2, nullable = false)
    private String floor;
}
