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
@Table(name = "doctor_details")
public class DoctorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
}
