package com.fhir_basic.learn_fhir.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fhir_basic.learn_fhir.dto.patientDto;
import com.fhir_basic.learn_fhir.entity.PatientDetails;
import com.fhir_basic.learn_fhir.repository.PatientRepository;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    FhirContext ctx = FhirContext.forR4();
    IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

    public PatientDetails addPatientDetails(Patient patient){
        PatientDetails response = new PatientDetails();
        response.setFirstName(patient.getName().get(0).getGiven().get(0).getValue());
        response.setLastName(patient.getName().get(0).getFamily());
        response.setGender(patient.getGender().toString());
        patientRepository.save(response);
        return response;
    }

    public Patient getPatientById(int patient_id){
        PatientDetails patientDetails = patientRepository.findById(patient_id)
                .orElseThrow(()->new RuntimeException("no patients"));

        Patient patient = new Patient();
        patient.addName()
                .setFamily(patientDetails.getLastName())
                .addGiven(patientDetails.getFirstName());

        patient.setGender(Enumerations.AdministrativeGender
                .fromCode(patientDetails.getGender().toLowerCase()));

        return patient;

    }

    public List<Patient> getAllPatients() {

        List<PatientDetails> patientDetailsList = patientRepository.findAll();

        List<Patient> patients = new ArrayList<>();

        for (PatientDetails data : patientDetailsList) {

            Patient patient = new Patient();

            patient.addName()
                    .setFamily(data.getLastName())
                    .addGiven(data.getFirstName());

            patient.setGender(Enumerations.AdministrativeGender
                    .fromCode(data.getGender().toLowerCase()));

            patients.add(patient);
        }

        return patients;
    }



    public String addPatientToFhirServer(patientDto patientReq){

        Patient patient = new Patient();
        Address address = new Address();

        patient.addName()
                .setFamily(patientReq.getLastName())
                .addGiven(patientReq.getFirstName());

        patient.setGender(Enumerations.AdministrativeGender
                .fromCode(patientReq.getGender().toLowerCase()));

        patient.setBirthDate(patientReq.getDateOfBirth());

        if(patientReq.getAddress()!=null) {

            address.addLine(patientReq.getAddress().getLine());
            address.setCity(patientReq.getAddress().getCity());
            address.setCountry(patientReq.getAddress().getCountry());
            address.setPostalCode(patientReq.getAddress().getPincode());
            address.setState(patientReq.getAddress().getState());
            patient.addAddress(address);

        }
        MethodOutcome outcome = client.create()
                .resource(patient)
                .execute();

        return outcome.getId().getIdPart();
    }

    public Patient getPatientByIdFromFhirServer(String id) {

        return client.read()
                .resource(Patient.class)
                .withId(id)
                .execute();
    }
}
