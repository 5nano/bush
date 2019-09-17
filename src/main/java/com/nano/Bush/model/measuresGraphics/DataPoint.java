package com.nano.Bush.model.measuresGraphics;

public class DataPoint {

    private String label;
    private Double y;

    public DataPoint(String label, Double y) {
        this.label = label;
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public Double getY() {
        return y;
    }

}
