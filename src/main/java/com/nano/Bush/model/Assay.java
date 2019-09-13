package com.nano.Bush.model;

public class Assay {

    private int idCrop;
    private String name;
    private String description;
    private int idUserCreator;

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
