package pl.medical.visits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.user.UserAddressData;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressData, Long> {
}
