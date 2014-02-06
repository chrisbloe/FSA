/*
 * Copyright (c) 2013 Scottish Friendly Assurance. All Rights Reserved.
 */
package com.custardcoding.fsa;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty(value = "FHRSEstablishment")
    private FhrsEstablishment fhrsestablishment;

    public Result() { }

    public FhrsEstablishment getFhrsEstablishment() {
        return fhrsestablishment;
    }

    public void setFhrsEstablishment(FhrsEstablishment fhrsestablishment) {
        this.fhrsestablishment = fhrsestablishment;
    }
}
