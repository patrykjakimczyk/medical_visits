package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medical.visits.model.entity.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}