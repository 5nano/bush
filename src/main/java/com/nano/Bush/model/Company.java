package com.nano.Bush.model;

public class Company {

    private String name;
    private String description;

    public Company(String name, String description) {
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

