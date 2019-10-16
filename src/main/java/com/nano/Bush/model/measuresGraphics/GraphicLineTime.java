package com.nano.Bush.model.measuresGraphics;

import java.time.LocalDate;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class GraphicLineTime {

    private final Integer treatmentId;
    private final LocalDate date;
    private final Double averagedValue;

    public GraphicLineTime(Integer treatmentId, LocalDate date, Double averagedValue) {
        this.treatmentId = treatmentId;
        this.date = date;
        this.averagedValue = averagedValue;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAveragedValue() {
        return averagedValue;
    }
}
