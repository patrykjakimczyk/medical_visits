package pl.medical.visits.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medical.visits.dto.PatientDTO;
import pl.medical.visits.model.user.Doctor;
import pl.medical.visits.model.user.Patient;
import pl.medical.visits.model.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user_t u where u.ROLE = 'PATIENT'", nativeQuery = true)
    List<Patient> findAllPatients();

    @Query(value = "select * from user_t u where u.ROLE = 'DOCTOR'", nativeQuery = true)
    List<Doctor> findAllDoctors();

    @Query(value = "select * from user_t u where u.ROLE = 'PATIENT'", nativeQuery = true)
    Page<Patient> findAllPatientsPaging(PageRequest pageable);

    @Query(value = "select u.id, u.ROLE, u.first_name, u.last_name, u.pesel, u.birth_date, u.sex," +
            " u.phone_nr, u.doctor_id from user_t u where u.ROLE = ?1 and u.doctor_id = ?2", nativeQuery = true)
    List<PatientDTO> findPatientsForDoctor(String role, long id);
}
