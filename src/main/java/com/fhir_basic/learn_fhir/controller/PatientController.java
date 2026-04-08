package com.fhir_basic.learn_fhir.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fhir_basic.learn_fhir.dto.patientDto;
import com.fhir_basic.learn_fhir.entity.PatientDetails;
import com.fhir_basic.learn_fhir.service.PatientService;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FHIR")
public class PatientController {

    @Autowired
    private PatientService patientService;

    FhirContext ctx =FhirContext.forR4();
    IParser parser = ctx.newJsonParser();

    @PostMapping("/add-patient")
    public ResponseEntity<PatientDetails> addPatientDetails(@RequestBody String json){
        Patient patient =parser.parseResource(Patient.class,json);
        return new ResponseEntity<>(patientService.addPatientDetails(patient), HttpStatus.OK);
    }

    @GetMapping("/patient-id/{patient_id}")
    public ResponseEntity<String> getPatientById(@PathVariable int patient_id){
        Patient patient = patientService.getPatientById(patient_id);

        String json = parser.encodeResourceToString(patient);
        return new ResponseEntity<>(json,HttpStatus.OK);

    }

    @GetMapping("/show-all-patients")
    public ResponseEntity<String> getAllPatients(){

        List<Patient> patients = patientService.getAllPatients();

        Bundle bundle = new Bundle();

        for(Patient patient : patients){
            bundle.addEntry().setResource(patient);
        }

        String json = parser.encodeResourceToString(bundle);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    // fhir server

    @PostMapping("/add-patient-fhir")
    public ResponseEntity<String> addPatientToFhir(@RequestBody patientDto request){

        String patient=patientService.addPatientToFhirServer(request);

        return new ResponseEntity<>(patient,HttpStatus.OK);

    }

    @GetMapping("/get-patient/{Id}")

    public ResponseEntity<String> getPatientDetailsFromFhirServer(@PathVariable String Id){

        Patient patient=patientService.getPatientByIdFromFhirServer(Id);

        String json = parser.encodeResourceToString(patient);

        return new ResponseEntity<>(json,HttpStatus.OK);

    }

}
