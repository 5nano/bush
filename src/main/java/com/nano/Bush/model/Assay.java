package com.nano.Bush.model;

import java.util.Optional;

public class Assay {

    private final Optional<Integer> idAssay;
    private final int idCrop;
    private final String name;
    private final String description;
    private final int idUserCreator;

    public Assay(Optional<Integer> idAssay, int idCrop, String name, String description, int idUserCreator) {
        this.idAssay = idAssay;
        this.idCrop = idCrop;
        this.name = name;
        this.description = description;
        this.idUserCreator = idUserCreator;
    }


    public int getIdCrop() {
        return idCrop;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIdUserCreator() {
        return idUserCreator;
    }

    public Optional<Integer> getIdAssay() {
        return idAssay;
    }
}
