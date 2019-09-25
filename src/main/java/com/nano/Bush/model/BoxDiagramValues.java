package com.nano.Bush.model;

/**
 * Created by Matias Zeitune sep. 2019
 **/
public class BoxDiagramValues {
    private final Double minimum;
    private final Double firstQuartile;
    private final Double median;
    private final Double thirdQuartile;
    private final Double maximum;

    public BoxDiagramValues(Double minimum, Double firstQuartile, Double median, Double thirdQuartile, Double maximum) {
        this.minimum = minimum;
        this.firstQuartile = firstQuartile;
        this.median = median;
        this.thirdQuartile = thirdQuartile;
        this.maximum = maximum;

    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getFirstQuartile() {
        return firstQuartile;
    }

    public Double getMedian() {
        return median;
    }

    public Double getThirdQuartile() {
        return thirdQuartile;
    }

    public Double getMaximum() {
        return maximum;
    }
}