package pl.medical.visits;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.medical.visits.model.entity.Office;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.repository.*;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner{

	private final UserLoginRepository loginRepository;
	private final SpecialityRepository specialityRepository;
	private final UserAddressRepository addressRepository;
	private final PasswordEncoder passwordEncoder;
	private final OfficeRepository officeRepository;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Admin admin = Admin.builder()
//				.role(Role.ADMIN)
//				.firstName("Adam")
//				.lastName("Nowak")
//				.pesel("86062711110")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432110")
//				.build();
//
//		UserLoginData userLoginData = UserLoginData.builder()
//				.user(admin)
//				.email("admin@admin.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		loginRepository.save(userLoginData);
//
//		Speciality speciality = Speciality.builder()
//				.specialityName("Gynecologist")
//				.build();
//
//		Speciality speciality2 = Speciality.builder()
//				.specialityName("Neurologist")
//				.build();
//
//		Speciality speciality3 = Speciality.builder()
//				.specialityName("Urologist")
//				.build();
//
//		Speciality speciality4 = Speciality.builder()
//				.specialityName("Endocrinologist")
//				.build();
//
//		Speciality speciality5 = Speciality.builder()
//				.specialityName("Cardiologist")
//				.build();
//
//		Speciality speciality6 = Speciality.builder()
//				.specialityName("Dentist")
//				.build();
//
//		specialityRepository.saveAll(List.of(speciality, speciality2, speciality3, speciality4, speciality5, speciality6));
//		List<Speciality> specialisties = specialityRepository.findAll();
//
//		Doctor doctor = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Karol")
//				.lastName("Nowak")
//				.pesel("86062711111")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432111")
//				.specialities(List.of(specialisties.get(0)))
//				.build();
//
//		UserLoginData userLoginData2 = UserLoginData.builder()
//				.user(doctor)
//				.email("doctor@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		Doctor doctor2 = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Michal")
//				.lastName("Podloga")
//				.pesel("86062711112")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432112")
//				.specialities(List.of(specialisties.get(1)))
//				.build();
//
//		UserLoginData userLoginData3 = UserLoginData.builder()
//				.user(doctor2)
//				.email("doctor2@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		Doctor doctor3 = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Andrzej")
//				.lastName("Moczara")
//				.pesel("86062711113")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432113")
//				.specialities(List.of(specialisties.get(2)))
//				.build();
//
//		UserLoginData userLoginData4 = UserLoginData.builder()
//				.user(doctor3)
//				.email("doctor3@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		Doctor doctor4 = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Patryk")
//				.lastName("Malinowy")
//				.pesel("86062711114")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432114")
//				.specialities(List.of(specialisties.get(3)))
//				.build();
//
//		UserLoginData userLoginData5 = UserLoginData.builder()
//				.user(doctor4)
//				.email("doctor4@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		Doctor doctor5 = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Kamil")
//				.lastName("Statula")
//				.pesel("86062711115")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432115")
//				.specialities(List.of(specialisties.get(4)))
//				.build();
//
//		UserLoginData userLoginData6 = UserLoginData.builder()
//				.user(doctor5)
//				.email("doctor5@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		Doctor doctor6 = Doctor.builder()
//				.role(Role.DOCTOR)
//				.firstName("Waldemar")
//				.lastName("Kapustka")
//				.pesel("86062711116")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432116")
//				.specialities(List.of(specialisties.get(5)))
//				.build();
//
//		UserLoginData userLoginData7 = UserLoginData.builder()
//				.user(doctor6)
//				.email("doctor6@doctor.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		//userRepository.saveAll(List.of(doctor, doctor2, doctor3, doctor4, doctor5, doctor6));
//		loginRepository.saveAll(List.of(userLoginData2, userLoginData3, userLoginData4, userLoginData5, userLoginData6, userLoginData7));
//
//		UserAddressData addressData = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Daniel")
//				.lastName("Sarna")
//				.pesel("86062711117")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432117")
//				.assignedDoctor(doctor)
//				//.addressData(addressData)
//				.build();
//
//		addressData.setUser(patient);
//		UserLoginData userLoginData8 = UserLoginData.builder()
//				.user(patient)
//				.email("patient@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData2 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient2 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Szymon")
//				.lastName("Kaluza")
//				.pesel("86062711118")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432118")
//				.assignedDoctor(doctor2)
//				//.addressData(addressData2)
//				.build();
//		addressData2.setUser(patient2);
//
//
//		UserLoginData userLoginData9 = UserLoginData.builder()
//				.user(patient2)
//				.email("patient2@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData3 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient3 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Wojciech")
//				.lastName("Zawada")
//				.pesel("86062711119")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432119")
//				.assignedDoctor(doctor3)
//				//.addressData(addressData3)
//				.build();
//		addressData3.setUser(patient3);
//
//		UserLoginData userLoginData10 = UserLoginData.builder()
//				.user(patient3)
//				.email("patient3@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData4 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient4 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Krystian")
//				.lastName("Zawada")
//				.pesel("86062711120")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432120")
//				.assignedDoctor(doctor4)
//				//.addressData(addressData4)
//				.build();
//		addressData4.setUser(patient4);
//
//		UserLoginData userLoginData11 = UserLoginData.builder()
//				.user(patient4)
//				.email("patient4@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData5 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient5 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Krystian")
//				.lastName("Plot")
//				.pesel("86062711122")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432122")
//				.assignedDoctor(doctor5)
//				//.addressData(addressData5)
//				.build();
//		addressData5.setUser(patient5);
//
//		UserLoginData userLoginData12 = UserLoginData.builder()
//				.user(patient5)
//				.email("patient5@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData6 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient6 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Igor")
//				.lastName("Kowalski")
//				.pesel("86062711123")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432123")
//				.assignedDoctor(doctor6)
//				//.addressData(addressData6)
//				.build();
//		addressData6.setUser(patient6);
//
//		UserLoginData userLoginData13 = UserLoginData.builder()
//				.user(patient6)
//				.email("patient6@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData7 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient7 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Damian")
//				.lastName("Kowalski")
//				.pesel("86062711124")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432124")
//				.assignedDoctor(doctor)
//				//.addressData(addressData7)
//				.build();
//		addressData7.setUser(patient7);
//
//		UserLoginData userLoginData14 = UserLoginData.builder()
//				.user(patient7)
//				.email("patient7@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData8 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient8 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Mariusz")
//				.lastName("Pudzian")
//				.pesel("86062711125")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432125")
//				.assignedDoctor(doctor4)
//				//.addressData(addressData8)
//				.build();
//		addressData8.setUser(patient8);
//
//		UserLoginData userLoginData15 = UserLoginData.builder()
//				.user(patient8)
//				.email("patient8@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData9 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient9 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Wladyslaw")
//				.lastName("Szary")
//				.pesel("86062711126")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432126")
//				.assignedDoctor(doctor5)
//				//.addressData(addressData9)
//				.build();
//		addressData9.setUser(patient9);
//
//
//		UserLoginData userLoginData16 = UserLoginData.builder()
//				.user(patient9)
//				.email("patient9@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData10 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient10 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Wladyslaw")
//				.lastName("Brazowy")
//				.pesel("86062711127")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("123432127")
//				.assignedDoctor(doctor4)
//				//.addressData(addressData10)
//				.build();
//		addressData10.setUser(patient10);
//
//		UserLoginData userLoginData17 = UserLoginData.builder()
//				.user(patient10)
//				.email("patient10@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		UserAddressData addressData11 = UserAddressData.builder()
//				.country("Poland")
//				.city("Szczecin")
//				.street("Prosta")
//				.houseNr("3")
//				.apartmentNr("21a")
//				.postalCode("11-111")
//				.build();
//
//		Patient patient11 = Patient.builder()
//				.role(Role.PATIENT)
//				.firstName("Konrad")
//				.lastName("Malomowny")
//				.pesel("86062711128")
//				.birthDate("1986-06-27")
//				.gender("Male")
//				.phoneNr("1234321288")
//				.assignedDoctor(doctor2)
//				//.addressData(addressData11)
//				.build();
//		addressData11.setUser(patient11);
//
//		UserLoginData userLoginData18 = UserLoginData.builder()
//				.user(patient11)
//				.email("patient11@patient.pl")
//				.password(passwordEncoder.encode("ZAQ!2wsx"))
//				.build();
//
//		loginRepository.saveAll(List.of(userLoginData8, userLoginData9, userLoginData10, userLoginData11,
//				userLoginData12, userLoginData13, userLoginData14, userLoginData15, userLoginData16, userLoginData17, userLoginData18));
//		addressRepository.saveAll(List.of(addressData, addressData2, addressData3, addressData4,
//				addressData5, addressData6, addressData7, addressData8, addressData9, addressData10, addressData11));
//
//		Office office = Office.builder()
//				.doctor(doctor)
//				.officeNr("123")
//				.floor("1")
//				.build();
//
//		Office office2 = Office.builder()
//				.doctor(doctor3)
//				.officeNr("124")
//				.floor("1")
//				.build();
//
//		Office office3 = Office.builder()
//				.doctor(doctor5)
//				.officeNr("212")
//				.floor("2")
//				.build();
//
//		officeRepository.saveAll(List.of(office, office2, office3));
	}
}
