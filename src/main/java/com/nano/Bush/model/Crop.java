package com.nano.Bush.model;

public class Crop {

    private String name;
    private String description;

    public Crop(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
