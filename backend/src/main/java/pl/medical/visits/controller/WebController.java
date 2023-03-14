package pl.medical.visits.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.medical.visits.model.Patient;
import pl.medical.visits.service.WebService;

@RestController("/")
@AllArgsConstructor
public class WebController {

    private WebService webService;

    @PostMapping("registerPatient")
    public Patient registerPatient(@RequestBody Patient givenPatient) {
        System.out.println(givenPatient);
        return webService.registerPatient(givenPatient);
    }
}
