package pl.medical.visits.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Patient;
import pl.medical.visits.model.entity.user.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user_t u where u.ROLE = 'DOCTOR'", nativeQuery = true)
    Page<Doctor> findAllDoctorsPaging(PageRequest pageable);

    @Query(value = "select * from user_t u where u.ROLE = 'PATIENT'", nativeQuery = true)
    Page<Patient> findAllPatientsPaging(PageRequest pageable);

    @Query(value = "select * from user_t u where u.ROLE = 'PATIENT' and u.doctor_id = ?1", nativeQuery = true)
    Page<Patient> findPatientsForDoctor(long id, PageRequest pageable);
}
