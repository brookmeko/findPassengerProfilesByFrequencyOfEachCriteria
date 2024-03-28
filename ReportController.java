package et.gov.customs.profile.service.application.controllers;

import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.ports.output.repository.ReportPassengerProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/customsOperations/passenger/report", produces = "application/vnd.api.v1+json")
public class ReportController {

    private final ReportPassengerProfileService reportPassengerProfileService;

    public ReportController(ReportPassengerProfileService reportPassengerProfileService) {

        this.reportPassengerProfileService = reportPassengerProfileService;
    }
@GetMapping("/riskLevel/frequencyOfEachCriteria/{startDate}/{endDate}/{reportFormat}")
    public ResponseEntity<byte[]> findPassengerProfilesByFrequencyOfEachCriteria(
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable String reportFormat) {

        return reportPassengerProfileService.findPassengerProfilesByFrequencyOfEachCriteria(startDate,endDate, reportFormat);
    }
}
