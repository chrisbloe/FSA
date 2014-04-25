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
public class EstablishmentDetail {
    @JsonProperty(value = "BusinessName")
    private String businessName;
    
    @JsonProperty(value = "RatingValue")
    private String ratingValue;
    
    @JsonProperty(value = "BusinessTypeID")
    private int businessTypeID;
    
    @JsonProperty(value = "AddressLine1")
    private String addressLine1;
    
    @JsonProperty(value = "AddressLine2")
    private String addressLine2;
    
    @JsonProperty(value = "AddressLine3")
    private String addressLine3;
    
    @JsonProperty(value = "AddressLine4")
    private String addressLine4;
    
    @JsonProperty(value = "PostCode")
    private String postCode;
    
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public int getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(int businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
