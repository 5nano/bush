package com.nano.Bush.model;

public class Experiment {

    private String name;
    private String description;

    public Experiment(String name, String description) {
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
