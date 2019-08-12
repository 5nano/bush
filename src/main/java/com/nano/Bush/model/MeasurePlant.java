package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurePlant {

    private LocalDate day;
    @JsonProperty("leafArea")
    private Double leafArea;
    public MeasurePlant(Double leafArea) {
        this.leafArea = leafArea;
    }

    public Double getLeafArea() {
        return leafArea;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
