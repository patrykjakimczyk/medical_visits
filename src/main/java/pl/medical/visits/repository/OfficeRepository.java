package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medical.visits.model.entity.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
