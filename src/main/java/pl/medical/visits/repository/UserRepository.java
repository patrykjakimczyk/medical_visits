package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByRole(Role role);

    @Query(value = "select * from user_t u where u.ROLE = ?1 and u.doctor_id = ?2", nativeQuery = true)
    List<User> findPatientsForDoctor(String role, long id);
}
