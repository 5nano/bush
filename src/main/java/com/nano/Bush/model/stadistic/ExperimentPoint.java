package com.nano.Bush.model.stadistic;

import java.time.LocalDate;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class ExperimentPoint {


    private final Integer assayId;
    private final Integer treatmentId;
    private final Integer experimentId;
    private final String pathImage;
    private final LocalDate timestamp;
    private final Double width;
    private final Double height;
    private final Double area;

    public ExperimentPoint(Integer assayId, Integer treatmentId, Integer experimentId, String pathImage, LocalDate timestamp, Double width, Double height, Double area) {
        this.assayId = assayId;
        this.treatmentId = treatmentId;
        this.experimentId = experimentId;
        this.pathImage = pathImage;
        this.timestamp = timestamp;
        this.width = width;
        this.height = height;
        this.area = area;
    }


    public Integer getAssayId() {
        return assayId;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public Integer getExperimentId() {
        return experimentId;
    }

    public String getPathImage() {
        return pathImage;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getArea() {
        return area;
    }
}
