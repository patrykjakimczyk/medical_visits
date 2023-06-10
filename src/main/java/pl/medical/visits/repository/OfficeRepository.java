package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medical.visits.model.entity.Office;
import pl.medical.visits.model.entity.user.Doctor;

import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    Optional<Office> findByDoctor(Doctor doctor);
}
