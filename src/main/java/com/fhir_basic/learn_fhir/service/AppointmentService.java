package com.fhir_basic.learn_fhir.service;

import com.fhir_basic.learn_fhir.entity.AppointmentDetails;
import com.fhir_basic.learn_fhir.repository.AppointmentRepository;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public String saveAppointment(Encounter encounter){

        AppointmentDetails appointment = new AppointmentDetails();

        // extract patientId
        String patientRef = encounter.getSubject().getReference();
        int patientId = Integer.parseInt(patientRef.split("/")[1]);

        // extract doctorId
        String doctorRef = encounter.getParticipantFirstRep()
                .getIndividual()
                .getReference();
        int doctorId = Integer.parseInt(doctorRef.split("/")[1]);

        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);

        String startDate = encounter.getPeriod().getStart().toString();

        if(encounter.hasPeriod() && encounter.getPeriod().hasStart()){
            LocalDate date = encounter.getPeriod()
                    .getStart()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            appointment.setDate(date);
        }


        appointmentRepository.save(appointment);

        return "Appointment saved successfully";
    }

    public Encounter getEncounterById(Integer id) {

        AppointmentDetails app = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        Encounter encounter = new Encounter();

        encounter.setId(app.getAppointmentId().toString());

        encounter.setSubject(new Reference("Patient/" + app.getPatientId()));
        encounter.addParticipant()
                .setIndividual(new Reference("Practitioner/" + app.getDoctorId()));

        encounter.setStatus(Encounter.EncounterStatus.FINISHED);
        Date date = java.sql.Date.valueOf(app.getDate());

        Period period = new Period();
        period.setStart(date);

        encounter.setPeriod(period);
        return encounter;
    }

}
