package pl.medical.visits.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medical.visits.model.user.Doctor;
import pl.medical.visits.model.user.Patient;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "visit")
public class Visit {
    @Id
    @SequenceGenerator(
            name = "visit_seq",
            sequenceName = "visit_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visit_seq")
    @Column(name = "visit_id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName= "user_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName= "user_id")
    private Doctor doctor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="ECT")
    @Column(name = "time_stamp", nullable = false)
    private Timestamp timeStamp;

    @Column
    private String description;
}
