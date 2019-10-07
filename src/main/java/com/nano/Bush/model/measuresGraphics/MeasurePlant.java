package com.nano.Bush.model.measuresGraphics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurePlant {

    private LocalDate day;

    @JsonProperty("area")
    private Area area;

    @JsonProperty("blue-yellow_frequencies")
    private Frequencies yellowFrequencies;

    private String image;


    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Frequencies getYellowFrequencies() {
        return yellowFrequencies;
    }

    public void setYellowFrequencies(Frequencies yellowFrequencies) {
        this.yellowFrequencies = yellowFrequencies;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
