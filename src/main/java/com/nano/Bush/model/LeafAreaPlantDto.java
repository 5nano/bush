package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeafAreaPlantDto {
    @JsonProperty("leafArea")
    private Double leafArea;


    public Double getLeafArea() {
        return leafArea;
    }

    public void setLeafArea(Double leafArea) {
        this.leafArea = leafArea;
    }
}
