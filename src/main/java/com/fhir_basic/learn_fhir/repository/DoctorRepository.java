package com.fhir_basic.learn_fhir.repository;

import com.fhir_basic.learn_fhir.entity.DoctorDetails;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorDetails,Integer> {
    public DoctorDetails findByFirstName(String name);

    public List<DoctorDetails> findByFirstNameContainingIgnoreCase(String name);
}
