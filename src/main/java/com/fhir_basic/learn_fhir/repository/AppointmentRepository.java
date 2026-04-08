package com.fhir_basic.learn_fhir.repository;

import com.fhir_basic.learn_fhir.entity.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentDetails, Integer> {
}
