package com.custardcoding.fsa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstablishmentCollection {
    @JsonProperty(value = "EstablishmentDetail")
    private ArrayList<EstablishmentDetail> establishmentDetails;

    public ArrayList<EstablishmentDetail> getEstablishmentDetails() {
        return establishmentDetails;
    }

    public void setEstablishmentDetails(ArrayList<EstablishmentDetail> establishmentDetails) {
        this.establishmentDetails = establishmentDetails;
    }
}
