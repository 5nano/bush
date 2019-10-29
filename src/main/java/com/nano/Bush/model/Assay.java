package com.nano.Bush.model;

import java.sql.Timestamp;
import java.util.Optional;

public class Assay {

    private  Optional<Integer> idAssay;
    private  int idCrop;
    private  String name;
    private  String description;
    private  int idUserCreator;
    private  Optional<AssayStatesEnum> state;
    private  Optional<Timestamp> created;

    public Assay(Optional<Integer> idAssay, int idCrop, String name, String description, int idUserCreator, Optional<AssayStatesEnum> state, Optional<Timestamp> created) {
        this.idAssay = idAssay;
        this.idCrop = idCrop;
        this.name = name;
        this.description = description;
        this.idUserCreator = idUserCreator;
        this.state = state;
        this.created = created;
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

    public Optional<AssayStatesEnum> getState() {
        return state;
    }

    public Optional<Timestamp> getCreated() {
        return created;
    }

    public void setIdUserCreator(int idUserCreator) {
        this.idUserCreator = idUserCreator;
    }
}
