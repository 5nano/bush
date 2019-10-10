package com.nano.Bush.model;

import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class Tag {


    private final Optional<Integer> idTag;
    private final String name;
    private final String description;


    public Tag(Optional<Integer> idTag, String name, String description) {
        this.idTag = idTag;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getIdTag() {
        return idTag;
    }

    public String getName() {
        return name;
    }
}
