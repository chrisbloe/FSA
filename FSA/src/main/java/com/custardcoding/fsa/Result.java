package com.custardcoding.fsa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 *
 * @author Kris Bloe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    @JsonProperty(value = "establishments")
    private ArrayList<Establishment> establishments;

    public ArrayList<Establishment> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(ArrayList<Establishment> establishments) {
        this.establishments = establishments;
    }
}
