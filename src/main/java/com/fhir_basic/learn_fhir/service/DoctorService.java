package com.fhir_basic.learn_fhir.service;

import com.fhir_basic.learn_fhir.entity.DoctorDetails;
import com.fhir_basic.learn_fhir.repository.DoctorRepository;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public DoctorDetails addDoctorDetails(Practitioner practitioner) {
        DoctorDetails response= new DoctorDetails();
        response.setFirstName(practitioner.getName().get(0).getGiven().get(0).getValue());
        response.setLastName(practitioner.getName().get(0).getFamily());
        response.setSpecialization("General");

        doctorRepository.save(response);
        return response;
    }

    public Practitioner getDoctorById(int doctorId) {

       DoctorDetails doctorDetails = doctorRepository.findById(doctorId)
                .orElseThrow(()->new RuntimeException("no doctors found"));

       Practitioner practitioner = new Practitioner();
       practitioner.addName().setFamily(doctorDetails.getLastName())
               .addGiven(doctorDetails.getFirstName());

       return practitioner;
    }

    public List<Practitioner> getAllDoctors(){

        List<DoctorDetails> doctorDetails=doctorRepository.findAll();
        List<Practitioner> practitionerList = new ArrayList<>();
        for(DoctorDetails datas : doctorDetails){
            Practitioner practitioner = new Practitioner();
            practitioner.addName().setFamily(datas.getLastName())
                    .addGiven(datas.getFirstName());
            practitionerList.add(practitioner);
        }
        return practitionerList;
    }

    public Practitioner getDoctorByName(String name) {
        Practitioner practitioner = new Practitioner();

        DoctorDetails doctorDetails =doctorRepository.findByFirstName(name);

        practitioner.addName().setFamily(doctorDetails.getLastName())
                .addGiven(doctorDetails.getFirstName());

        return practitioner;

    }

    public List<Practitioner> searchDoctorByName(String name) {

       List<DoctorDetails> listOfDoctors= doctorRepository.findByFirstNameContainingIgnoreCase(name);

       List<Practitioner> searchedDocters = new ArrayList<>();

       for(DoctorDetails details : listOfDoctors){

           Practitioner practitioner = new Practitioner();
           practitioner.addName().setFamily(details.getLastName())
                   .addGiven(details.getFirstName());
           practitioner.addQualification().getCode().setText(details.getSpecialization());
           searchedDocters.add(practitioner);
       }

        return searchedDocters;
    }
}
