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
public class FhrsEstablishment {
    @JsonProperty(value = "EstablishmentCollection")
    private EstablishmentCollection establishmentCollection;

    public FhrsEstablishment() { }

    public EstablishmentCollection getEstablishmentCollection() {
        return establishmentCollection;
    }

    public void setEstablishmentCollection(EstablishmentCollection establishmentCollection) {
        this.establishmentCollection = establishmentCollection;
    }
}
