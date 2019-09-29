package com.nano.Bush.model.stadistic;

import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramaByExperiment {
    private final Integer experimentId;
    private final Set<Double> values;

    public BoxDiagramaByExperiment(Integer experimentId, Set<Double> values) {
        this.experimentId = experimentId;
        this.values = values;
    }

    public Integer getExperimentId() {
        return experimentId;
    }

    public Set<Double> getValues() {
        return values;
    }
}
