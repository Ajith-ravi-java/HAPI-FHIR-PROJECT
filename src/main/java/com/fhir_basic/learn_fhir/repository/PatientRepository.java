package com.fhir_basic.learn_fhir.repository;

import com.fhir_basic.learn_fhir.entity.PatientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientDetails,Integer> {

}
