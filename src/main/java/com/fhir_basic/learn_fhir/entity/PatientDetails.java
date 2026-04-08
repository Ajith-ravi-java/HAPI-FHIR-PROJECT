package com.fhir_basic.learn_fhir.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "patient_details")
public class PatientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    private String firstName;
    private String lastName;
    private String gender;


}
