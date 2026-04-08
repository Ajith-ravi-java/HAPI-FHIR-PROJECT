package com.fhir_basic.learn_fhir.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String line;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
