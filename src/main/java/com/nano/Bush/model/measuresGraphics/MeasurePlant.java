package com.nano.Bush.model.measuresGraphics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurePlant {

    private LocalDate day;

    private Instant dayWithHour;

    @JsonProperty("area")
    private PlantCvValue area;

    private PlantCvValue width;

    private PlantCvValue height;

    @JsonProperty("blue-yellow_frequencies")
    private Frequencies yellowFrequencies;

    @JsonProperty("green_frequencies")
    private Frequencies greenFrequencies;

    private String image;


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

    public Frequencies getGreenFrequencies() {
        return greenFrequencies;
    }

    public void setGreenFrequencies(Frequencies greenFrequencies) {
        this.greenFrequencies = greenFrequencies;
    }

    public PlantCvValue getHeight() {
        return height;
    }

    public PlantCvValue getWidth() {
        return width;
    }

    public void setWidth(PlantCvValue width) {
        this.width = width;
    }

    public Instant getDayWithHour() {
        return dayWithHour;
    }

    public void setDayWithHour(Instant dayWithHour) {
        this.dayWithHour = dayWithHour;
    }
}
