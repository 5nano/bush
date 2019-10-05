package com.nano.Bush.model;

import java.util.Optional;

public class Mixture {

    private final Optional<Integer> idMixture;
    private final String name;
    private final String description;

    public Mixture(Optional<Integer> idMixture, String name, String description) {
        this.idMixture = idMixture;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getIdMixture() {
        return idMixture;
    }
}
