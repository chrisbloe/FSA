/*
 * Copyright (c) 2013 Scottish Friendly Assurance. All Rights Reserved.
 */
package com.custardcoding.fsa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty(value = "FHRSEstablishment")
    private FhrsEstablishment fhrsestablishment;

    public FhrsEstablishment getFhrsEstablishment() {
        return fhrsestablishment;
    }

    public void setFhrsEstablishment(FhrsEstablishment fhrsestablishment) {
        this.fhrsestablishment = fhrsestablishment;
    }
}
