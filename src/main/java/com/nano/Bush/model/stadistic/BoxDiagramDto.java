package com.nano.Bush.model.stadistic;

import java.util.List;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramDto {

    private final Integer treatmentId;
    private final List<Double> values;

    public BoxDiagramDto(Integer treatmentId, List<Double> values) {
        this.treatmentId = treatmentId;
        this.values = values;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public List<Double> getValues() {
        return values;
    }

}
