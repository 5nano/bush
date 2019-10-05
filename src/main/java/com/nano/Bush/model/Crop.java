package com.nano.Bush.model;

import java.util.Optional;

public class Crop {

    private final Optional<Integer> idCrop;
    private final String name;
    private final String description;

    public Crop(Optional<Integer> idCrop, String name, String description) {
        this.idCrop = idCrop;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getIdCrop() {
        return idCrop;
    }
}
