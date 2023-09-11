package pl.medical.visits.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.*;
import pl.medical.visits.model.dto.*;
import pl.medical.visits.model.entity.Office;
import pl.medical.visits.model.entity.Visit;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.request.*;
import pl.medical.visits.repository.*;
import pl.medical.visits.service.VisitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final VisitRepository visitRepository;
    private final OfficeRepository officeRepository;
    private final SpecialityRepository specialityRepository;

    public List<SpecialityDTO> getSpecialities() {
        return this.specialityRepository
                .findAll()
                .stream()
                .map(SpecialityDTO::new)
                .collect(Collectors.toList());
    }

    public VisitDTO registerVisit(RegisterVisitRequest visitRequest, String email) throws NotUniqueValueException {
//        if (this.canAuthUserAccessUserOfId(authUserEmail, visitWrapper.getPatientId(), Role.PATIENT)) {

            UserLoginData loginData = this.userLoginRepository.findByEmail(email);
            Optional<Doctor> doctorOptional = this.userRepository.findDoctorById(visitRequest.getDoctorId());

            if (!loginData.getUser().getRole().equals(Role.PATIENT)) {
                throw new UserPerformedForbiddenActionException("Only patient can register visit!");
            }

            Patient patient = (Patient) loginData.getUser();

            if (doctorOptional.isEmpty()) {
                throw new UserDoesNotExistException(
                        "Cannot register visit because patient or doctor with given id does not exist!"
                );
            }

            Doctor doctor = doctorOptional.get();

            Visit visit = new Visit();
            visit.setPatient(patient);
            visit.setDoctor(doctor);
            visit.setDescription(visitRequest.getDescription());
            visit.setTimeStamp(visitRequest.getTimestamp());

            Optional<Office> officeOptional = officeRepository.findByDoctor(doctor);
            visit.setOffice(
                    officeOptional.orElseThrow(() ->
                            new DoctorDoesNotHaveAssignedOfficeException("Doctor does not have assigned office!"))
            );

            try {
                visitRepository.save(visit);
            } catch (RuntimeException e) {
                throw new NotUniqueValueException("Cannot register visit for occupied date");
            }

            return new VisitDTO(visit, email);
//        }
//        throw new UserPerformedForbiddenActionException("You are not allowed to register visit for another patient");
    }

    public Page<VisitDTO> getAllVisits(Map<String, String> reqParams, String email) {
        User user = this.getUserByEmail(email);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new UserPerformedForbiddenActionException("Only admin can access list of all visits!");
        }

        final PageRequest pageRequest = this.createPageRequest(reqParams).withSort(Sort.Direction.DESC, "timeStamp");
        return visitRepository.findAll(pageRequest)
                .map(visit -> new VisitDTO(visit, null));
    }

    public VisitDTO getVisitData(Long visitId, String email) {
        Optional<Visit> visitOptional = visitRepository.findById(visitId);

        if (visitOptional.isEmpty()) {
            throw new UserPerformedForbiddenActionException("Visit with given id does not exist");
        }

        Visit visit = visitOptional.get();
        User user = this.getUserByEmail(email);

        if (visit.getDoctor().getId() != user.getId()) {
            if (user.getRole().equals(Role.ADMIN)) {
                return new VisitDTO(visit, null);
            }
            throw new UserPerformedForbiddenActionException("You cannot access this visit's data");
        }
        return new VisitDTO(visit, null);
    }

    public Page<VisitDTO> getAllDoctorVisits(Map<String, String> reqParams, String email) {
        User user = this.getUserByEmail(email);

        if (!user.getRole().equals(Role.DOCTOR)) {
            throw new UserPerformedForbiddenActionException("You cannot access those visits' data");
        }

        final PageRequest pageRequest = this.createPageRequest(reqParams).withSort(Sort.Direction.DESC, "time_stamp");
        return visitRepository.findAllDoctorVisits(user.getId(), pageRequest)
                .map(visit -> new VisitDTO(visit, null));
    }

    public VisitDTO updateVisit(EditVisitWrapper givenVisit, String email) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        if (authenticatedUser.isEmpty()) {
            throw new UserDoesNotExistException("User from given token does not exist");
        }

        User user = authenticatedUser.get();

        Optional<Visit> visitOptional = visitRepository.findById(givenVisit.getId());

        if(visitOptional.isEmpty()) {
            throw new UserPerformedForbiddenActionException("Cannot update visit which does not exist");
        }
        Visit visit = visitOptional.get();

        if (givenVisit.getDoctorId() != user.getId()
                && !user.getRole().equals(Role.ADMIN)
                && visit.getDoctor().getId() == givenVisit.getDoctorId()
        ) {
            throw new UserPerformedForbiddenActionException("You cannot access those visits' data");
        }

        visit.setDescription(givenVisit.getDescription());
        visit.setTimeStamp(givenVisit.getTimeStamp());

        visitRepository.save(visit);
        return new VisitDTO(visit, null);
    }

    private User getUserByEmail(String email) {
        UserLoginData authenticatedUsersLoginData = userLoginRepository.findByEmail(email);
        Optional<User> authenticatedUser = userRepository.findById(authenticatedUsersLoginData.getUser().getId());

        return authenticatedUser.orElseThrow(
                () -> new UserDoesNotExistException("User from given token does not exist")
        );
    }

    private PageRequest createPageRequest(Map<String, String> reqParams) {
        int offset;
        int pageSize;

        try {
            offset = Integer.parseInt(reqParams.get("offset"));
            pageSize = Integer.parseInt(reqParams.get("pageSize"));
        } catch (NumberFormatException e) {
            throw new WrongRequestParametersException("Invalid request parameters (no paging information)");
        }

        List<Sort.Order> sorts = this.findSorts(reqParams);

        if (sorts.isEmpty()) return PageRequest.of(offset, pageSize);
        else return PageRequest.of(offset, pageSize, Sort.by(sorts));
    }

    private List<Sort.Order> findSorts(Map<String, String> reqParams) {
        String firstName = "first_name";
        String lastName = "last_name";
        List<Sort.Order> sorts = new ArrayList<>();

        if (reqParams.get(firstName) != null) {
            if (reqParams.get(firstName).equals("DESC"))
                sorts.add(new Sort.Order(Sort.Direction.DESC, firstName));
            else
                sorts.add(new Sort.Order(Sort.Direction.ASC, firstName));
        }

        if (reqParams.get(lastName) != null) {
            if (reqParams.get(lastName).equals("DESC"))
                sorts.add(new Sort.Order(Sort.Direction.DESC, lastName));
            else
                sorts.add(new Sort.Order(Sort.Direction.ASC, lastName));
        }
        return sorts;
    }
}
