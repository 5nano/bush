package com.nano.Bush.model;

public class Assay {

    private final int idCrop;
    private final String name;
    private final String description;
    private final int idUserCreator;

    public Assay(int idCrop, String name, String description, int idUserCreator) {
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
}
