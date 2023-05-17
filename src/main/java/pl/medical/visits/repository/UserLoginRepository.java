package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.entity.user.User;
import pl.medical.visits.model.entity.user.UserLoginData;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginData, Long> {
    UserLoginData findByEmail(String email);
    @Query(value = "select ul.email from user_login ul where ul.user_id = ?1", nativeQuery = true)
    String findEmailForUserId(Long id);

    @Query(value = "select * from user_login ul where ul.user_id = ?1", nativeQuery = true)
    Optional<UserLoginData> findByUser(long id);
}
