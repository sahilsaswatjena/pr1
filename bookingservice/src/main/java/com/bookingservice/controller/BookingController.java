package com.bookingservice.controller;

import com.bookingservice.client.DoctorClient;
import com.bookingservice.client.PatientClient;
import com.bookingservice.dto.Doctor;
import com.bookingservice.dto.DoctorAppointmentSchedule;
import com.bookingservice.dto.Patient;
import com.bookingservice.dto.TimeSlots;
import com.bookingservice.entity.BookingConfirmation;
import com.bookingservice.repository.BookingConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private PatientClient patientClient;
    @Autowired
    private BookingConfirmationRepository bookingConfirmationRepository;
//http://localhost:8085/api/v1/booking/getdoctor?doctorId=1&patientId=1&date=2025-12-25&time=10:30
    @GetMapping("/getdoctor")
    public String getDoctorById(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time

    ) {
        Patient p = patientClient.getPatientById(patientId);
        Doctor d = doctorClient.getDoctorById(doctorId);
        BookingConfirmation bookingconfirmation = new BookingConfirmation();
        bookingconfirmation.setDoctorName(d.getName());
        bookingconfirmation.setPatientName(p.getName());
        bookingconfirmation.setAddress(d.getAddress());
        List<DoctorAppointmentSchedule > appointmentSchedules = d.getAppointmentSchedules();
        for(DoctorAppointmentSchedule app:appointmentSchedules){
            LocalDate localDate = app.getDate();
            if(localDate.isEqual(date)) {
                List<TimeSlots> timeSlots = app.getTimeSlots();
                for (TimeSlots t : timeSlots) {
                    if(timeSlots.equals(time)){
                        System.out.println("Complete Booking");
                    }
                }
            }
        }



        BookingConfirmation savedBookingConfirmation = bookingConfirmationRepository.save(bookingconfirmation);
        return "Done";

    }
}