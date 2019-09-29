package com.nano.Bush.model.stadistic;

import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramDto {

    private final Set<BoxDiagramaByExperiment> yellowFrequencies;

    public BoxDiagramDto(Set<BoxDiagramaByExperiment> yellowFrequencies) {
        this.yellowFrequencies = yellowFrequencies;
    }


    public Set<BoxDiagramaByExperiment> getYellowFrequencies() {
        return yellowFrequencies;
    }
}
