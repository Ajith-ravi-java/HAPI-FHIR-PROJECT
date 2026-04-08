package com.fhir_basic.learn_fhir.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "appointment_details")
public class AppointmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;
    private int patientId;
    private int doctorId;
    private LocalDate date;

}
