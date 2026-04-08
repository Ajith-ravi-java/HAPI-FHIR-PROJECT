package com.fhir_basic.learn_fhir.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.fhir_basic.learn_fhir.entity.DoctorDetails;
import com.fhir_basic.learn_fhir.service.DoctorService;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FHIR")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    FhirContext ctx =FhirContext.forR4();
    IParser parser = ctx.newJsonParser();

    @PostMapping("/add-doctor")
    public ResponseEntity<DoctorDetails> addDoctorDetails(@RequestBody String json){
        Practitioner practitioner= parser.parseResource(Practitioner.class,json);
        return new ResponseEntity<>(doctorService.addDoctorDetails(practitioner), HttpStatus.OK);

    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<String> getDoctorById(@PathVariable int doctorId){
        Practitioner practitioner = doctorService.getDoctorById(doctorId);
        String json =parser.encodeResourceToString(practitioner);
        return new ResponseEntity<>(json,HttpStatus.OK);
    }

    @GetMapping("/all-doctors")
    public ResponseEntity<String> getAllDoctors(){

       List<Practitioner> practitionerList= doctorService.getAllDoctors();

        Bundle bundle = new Bundle();

        for(Practitioner practitioner : practitionerList ){
            bundle.addEntry().setResource(practitioner);
        }
        String json =parser.encodeResourceToString(bundle);
        return new ResponseEntity<>(json,HttpStatus.OK);
    }

    @GetMapping("/get-doctor/{name}")
    public ResponseEntity<String> getDoctorByName(@PathVariable String name){
        Practitioner practitioner=doctorService.getDoctorByName(name);
        String json = parser.encodeResourceToString(practitioner);
        return new ResponseEntity<>(json,HttpStatus.OK);
    }

    @GetMapping("/doctor/search")
    public ResponseEntity<String> searchDoctor(@RequestParam String name){

        List<Practitioner> practitioner = doctorService.searchDoctorByName(name);
        Bundle bundle = new Bundle();
        for(Practitioner p : practitioner){
            bundle.addEntry().setResource(p);
        }
        String json = parser.encodeResourceToString(bundle);
        return new ResponseEntity<>(json,HttpStatus.OK);
    }

}
