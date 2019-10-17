package com.nano.Bush.model.measuresGraphics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Observations {
    @JsonProperty("area")
    private PlantCvValue area;

    @JsonProperty("blue-yellow_frequencies")
    private Frequencies yellowFrequencies;

    public PlantCvValue getArea() {
        return area;
    }

    public void setArea(PlantCvValue area) {
        this.area = area;
    }

    public Frequencies getYellowFrequencies() {
        return yellowFrequencies;
    }

    public void setYellowFrequencies(Frequencies yellowFrequencies) {
        this.yellowFrequencies = yellowFrequencies;
    }
}
