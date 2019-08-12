package com.nano.Bush.model;

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
