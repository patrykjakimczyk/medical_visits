package pl.medical.visits.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.medical.visits.model.entity.user.Doctor;
import pl.medical.visits.model.entity.user.Patient;
import pl.medical.visits.model.entity.user.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String SELECT_DOCTOR = "select * from user_t u where u.ROLE = 'DOCTOR'";
    String SELECT_PATIENT = "select * from user_t u where u.ROLE = 'PATIENT'";
    String SELECT_PATIENT_W_DOCTOR_ID = "select * from user_t u where u.ROLE = 'PATIENT' and u.doctor_id = ?1";

    @Query(value = SELECT_PATIENT + " and u.user_id = ?1", nativeQuery = true)
    Optional<Patient> findPatientById(long id);

    @Query(value = SELECT_DOCTOR + " and u.user_id = ?1", nativeQuery = true)
    Optional<Doctor> findDoctorById(long id);

    @Query(value = SELECT_DOCTOR, nativeQuery = true)
    Page<Doctor> findAllDoctorsPaging(PageRequest pageable);

    @Query(value = SELECT_DOCTOR + " and UPPER(u.first_name) like UPPER(CONCAT(?1, '%'))", nativeQuery = true)
    Page<Doctor> findAllDoctorsWithFirstNamePaging(String firstName, PageRequest pageable);

    @Query(value = SELECT_DOCTOR + " and UPPER(u.last_name) like UPPER(CONCAT(?1, '%'))", nativeQuery = true)
    Page<Doctor> findAllDoctorsWithLastNamePaging(String lastName, PageRequest pageable);

    @Query(value = SELECT_DOCTOR + " and u.pesel like CONCAT(?1, '%')", nativeQuery = true)
    Page<Doctor> findAllDoctorsWithPeselPaging(String pesel, PageRequest pageable);

    @Query(value = SELECT_PATIENT, nativeQuery = true)
    Page<Patient> findAllPatientsPaging(PageRequest pageable);

    @Query(value = SELECT_PATIENT + " and UPPER(u.first_name) like UPPER(CONCAT(?1, '%'))", nativeQuery = true)
    Page<Patient> findAllPatientsWithFirstNamePaging(String firstName, PageRequest pageable);

    @Query(value = SELECT_PATIENT + " and UPPER(u.last_name) like UPPER(CONCAT(?1, '%'))", nativeQuery = true)
    Page<Patient> findAllPatientsWithLastNamePaging(String lastName, PageRequest pageable);

    @Query(value = SELECT_PATIENT + " and u.pesel like CONCAT(?1, '%')", nativeQuery = true)
    Page<Patient> findAllPatientsWithPeselPaging(String pesel, PageRequest pageable);

    @Query(value = SELECT_PATIENT_W_DOCTOR_ID, nativeQuery = true)
    Page<Patient> findPatientsForDoctor(long id, PageRequest pageable);

    @Query(value = SELECT_PATIENT_W_DOCTOR_ID + " and UPPER(u.firstName) like UPPER(CONCAT(?2, '%'))", nativeQuery = true)
    Page<Patient> findPatientsWithFirstNameForDoctor(long id, String first_name, PageRequest pageable);

    @Query(value = SELECT_PATIENT_W_DOCTOR_ID + " and UPPER(u.lastName) like UPPER(CONCAT(?2, '%'))", nativeQuery = true)
    Page<Patient> findPatientsWithLastNameForDoctor(long id, String last_name, PageRequest pageable);

    @Query(value = SELECT_PATIENT_W_DOCTOR_ID + " and u.pesel like CONCAT(?2, '%')", nativeQuery = true)
    Page<Patient> findPatientsWithPeselForDoctor(long id, String pesel, PageRequest pageable);

    @Query(value = "select distinct u.* from user_t u join doctor_speciality ds ON ds.doctor_id = u.user_id JOIN speciality s ON s.speciality_id = ds.speciality_id where s.speciality_id = ?1", nativeQuery = true)
    Page<Doctor> findDoctorsWithSpeciality(Long specialityId, PageRequest pageable);
    @Query(value = "select distinct u.* from user_t u join doctor_speciality ds ON ds.doctor_id = u.user_id JOIN speciality s ON s.speciality_id = ds.speciality_id where s.speciality_id = ?1 and UPPER(u.firstName) like UPPER(CONCAT(?2, '%'))", nativeQuery = true )
    Page<Doctor> findDoctorsWithSpecialityAndFirstName(Long specialityId, String firstName, PageRequest pageable);
    @Query(value = "select distinct u.* from user_t u join doctor_speciality ds ON ds.doctor_id = u.user_id JOIN speciality s ON s.speciality_id = ds.speciality_id where s.speciality_id = ?1 and UPPER(u.lastName) like UPPER(CONCAT(?2, '%'))", nativeQuery = true)
    Page<Doctor> findDoctorsWithSpecialityAndLastName(Long specialityId, String lastName, PageRequest pageable);
}
//u.user_id, u.role, u.first_name, u.last_name, u.pesel, u.birthDate, u.sex, u.phoneNr
