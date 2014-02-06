/*
 * Copyright (c) 2013 Scottish Friendly Assurance. All Rights Reserved.
 */
package com.custardcoding.fsa;

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstablishmentCollection {
    @JsonProperty(value = "EstablishmentDetail")
    private ArrayList<EstablishmentDetail> establishmentDetails;

    public EstablishmentCollection() { }

    public ArrayList<EstablishmentDetail> getEstablishmentDetails() {
        return establishmentDetails;
    }

    public void setEstablishmentDetails(ArrayList<EstablishmentDetail> establishmentDetails) {
        this.establishmentDetails = establishmentDetails;
    }
}
