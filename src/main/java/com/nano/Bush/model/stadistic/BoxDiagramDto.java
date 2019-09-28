package com.nano.Bush.model.stadistic;

import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramDto {

    private final Set<StadisticValue> yellowFrequencies;

    public BoxDiagramDto(Set<StadisticValue> yellowFrequencies) {
        this.yellowFrequencies = yellowFrequencies;
    }


    public Set<StadisticValue> getYellowFrequencies() {
        return yellowFrequencies;
    }
}
