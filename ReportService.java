package et.gov.customs.profile.service.services;


import et.gov.customs.profile.service.domain.dto.search.ReportPassengerRiskDTO;
import et.gov.customs.profile.service.domain.ports.output.repository.ReportPassengerProfileService;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportPassengerProfileService {

    private final ReportCommandHandler reportCommandHandler;

    public ReportServiceImpl(ReportCommandHandler reportCommandHandler) {
        this.reportCommandHandler = reportCommandHandler;

    }


    private ResponseEntity<byte[]> generateReport(List<ReportPassengerRiskDTO> reportList, String filePath){
        try {
            Locale locale = new Locale("am"); // Amharic

            ClassPathResource resource = new ClassPathResource(filePath);

            JasperReport jasperReport = null;

            jasperReport = JasperCompileManager.compileReport(resource.getInputStream());


            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(JRParameter.REPORT_LOCALE, locale);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Export the report to PDF stream with UTF-8 encoding and Amharic font
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            outputStream.close();

            byte[] reportBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(reportBytes.length);

            return ResponseEntity.ok().headers(headers).body(reportBytes);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    @SneakyThrows
    public ResponseEntity<byte[]> findPassengerProfilesByFrequencyOfEachCriteria(String startDate, String endDate, String reportFormat) {

        //UUID branch_id = UUID.fromString(branchId);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(pattern);
        Date parsedDate = inputDateFormat.parse(startDate);

        Timestamp sDate = new Timestamp(parsedDate.getTime());


        parsedDate = inputDateFormat.parse(endDate);
        Timestamp eDate =  new Timestamp(parsedDate.getTime());


        List<ReportPassengerRiskDTO> reportList = this.reportCommandHandler.findPassengerProfilesByFrequencyOfEachCriteria(sDate, eDate);


        String filePath = "";

        if (reportFormat.equalsIgnoreCase("table")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteria.jrxml";;
        }
        else if (reportFormat.equalsIgnoreCase("chart")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteriaChart.jrxml";
        }
        else if (reportFormat.equalsIgnoreCase("table with chart")){
            filePath = "report/rptPassengerProfilesByFrequencyOfEachCriteriaChartAndTable.jrxml";
        }

        return generateReport(reportList,filePath);


    }
