package com.bookingservice.client;

import com.bookingservice.dto.Doctor;
import com.bookingservice.dto.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "patientservice", url = "http://localhost:8082/api/v1/patient")//it should be matching with
// patient service aplication.proporties i.e. jdbc:mysql://localhost:3306/patient_db
public interface PatientClient {
    @GetMapping("/getpatientbyid")
    public Patient getPatientById(@RequestParam long id);
}
