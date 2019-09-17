package com.nano.Bush.model;

public class Agrochemical {

    private final String name;
    private final String description;

    public Agrochemical(String name, String description) {
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
