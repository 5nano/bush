package com.nano.Bush.model;

public class MeasurePlant {

    private String day;
    private String leafArea;
    private String witdh;

    public MeasurePlant(String day, String leafArea, String witdh) {
        this.day = day;
        this.leafArea = leafArea;
        this.witdh = witdh;
    }

    public String getWitdh() {
        return witdh;
    }

    public String getLeafArea() {
        return leafArea;
    }

    public String getDay() {
        return day;
    }
}
