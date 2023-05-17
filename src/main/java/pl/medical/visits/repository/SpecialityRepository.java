package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.medical.visits.model.entity.user.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
