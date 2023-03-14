package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.Patient;
import pl.medical.visits.model.User;

@Repository
public interface WebRepository extends JpaRepository<User, Long> {

    User save(Patient patient);
}
