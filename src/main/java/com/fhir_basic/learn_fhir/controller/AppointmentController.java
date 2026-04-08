package com.fhir_basic.learn_fhir.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fhir_basic.learn_fhir.service.AppointmentService;
import org.hl7.fhir.r4.model.Encounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("FHIR")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    FhirContext cfx = FhirContext.forR4();
    IParser parser = cfx.newJsonParser();

    @PostMapping("/add-appointment")
    public ResponseEntity<String> createAppointment(@RequestBody String json){

        Encounter encounter = parser.parseResource(Encounter.class, json);

        return new ResponseEntity<>(appointmentService.saveAppointment(encounter), HttpStatus.OK);
    }

    @GetMapping("getAppointment/{appointment_id}")
    public ResponseEntity<String> getAppointmentById(@PathVariable int appointment_id){
        Encounter encounter=  appointmentService.getEncounterById(appointment_id);
        String json = parser.encodeResourceToString(encounter);
        return new ResponseEntity<>(json,HttpStatus.OK);
    }


}
