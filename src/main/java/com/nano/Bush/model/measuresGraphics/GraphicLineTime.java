package com.nano.Bush.model.measuresGraphics;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/

//	ignore null fields , class level
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GraphicLineTime {

    private final LocalDate date;
    private final Double value;
    private final String imagePath;

    public GraphicLineTime(LocalDate date, Double value, String imagePath) {
        this.date = date;
        this.value = value;
        this.imagePath = imagePath;
    }


    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }
}
