package pl.medical.visits.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "office")
public class Office {
    @Id
    @Column(name = "office_id", unique = true, nullable = false)
    private long id;

    @OneToOne
    @JoinColumn(name = "doctor_id", unique = true, nullable = false)
    private Doctor doctor;

    @Column(name = "office_nr", length = 3, unique = true, nullable = false)
    private String officeNr;

    @Column(length = 2, nullable = false)
    private String floor;
}
