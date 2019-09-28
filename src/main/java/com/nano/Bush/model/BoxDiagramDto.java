package com.nano.Bush.model;

import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramDto {

    private final Set<Double> yellowFrequencies;

    public BoxDiagramDto(Set<Double> yellowFrequencies) {
        this.yellowFrequencies = yellowFrequencies;
    }


    public Set<Double> getYellowFrequencies() {
        return yellowFrequencies;
    }
}
