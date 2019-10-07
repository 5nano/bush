package com.nano.Bush.model.measuresGraphics;

public class DataPoint {

    private String label;
    private Double y;
    private String pathImage;

    public DataPoint(String label, Double y, String pathImage) {
        this.label = label;
        this.y = y;
        this.pathImage = pathImage;
    }

    public String getLabel() {
        return label;
    }

    public Double getY() {
        return y;
    }

    public String getPathImage() {
        return pathImage;
    }
}
