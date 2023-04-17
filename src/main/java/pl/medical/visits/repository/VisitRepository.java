package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
