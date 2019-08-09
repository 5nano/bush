package com.nano.Bush.model;

public class DataPoint {

    public DataPoint(String label, Double y) {
        this.label = label;
        this.y = y;
    }

    private String label;

    private Double y;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }


}
