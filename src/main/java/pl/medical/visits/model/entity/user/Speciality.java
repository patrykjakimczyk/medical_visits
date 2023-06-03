package pl.medical.visits.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Speciality {
    @Id
    @SequenceGenerator(
            name = "speciality_seq",
            sequenceName = "speciality_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "speciality_seq")
    @Column(name = "speciality_id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "speciality_name", length = 50, unique = true, nullable = false)
    private String specialityName;
}
