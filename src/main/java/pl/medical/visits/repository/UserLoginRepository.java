package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.entity.user.UserLoginData;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginData, Long> {
    UserLoginData findByEmail(String email);
}
