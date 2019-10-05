package com.nano.Bush.model;

import java.util.Optional;

public class Agrochemical {

    private final Optional<Integer> idAgrochemical;
    private final String name;
    private final String description;

    public Agrochemical(Optional<Integer> idAgrochemical, String name, String description) {
        this.idAgrochemical = idAgrochemical;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getIdAgrochemical() {
        return idAgrochemical;
    }
}
