package com.nano.Bush.model.stadistic;

import java.util.List;
import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramaByExperiment {
    private final Integer experimentId;
    private final Integer treatmentId;
    private final List<Double> values;

    public BoxDiagramaByExperiment(Integer experimentId, Integer treatmentId, List<Double> values) {
        this.experimentId = experimentId;
        this.treatmentId = treatmentId;
        this.values = values;
    }

    public Integer getExperimentId() {
        return experimentId;
    }

    public List<Double> getValues() {
        return values;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }
}
