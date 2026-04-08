package com.fhir_basic.learn_fhir.dto;

import lombok.Data;

import java.util.Date;

@Data
public class patientDto {

    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private AddressDTO address;

}
