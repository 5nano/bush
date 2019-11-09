package com.nano.Bush.model;

import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class Tag {


    private final Optional<Integer> idTag;
    private final String name;
    private final String description;
    private final String color;


    public Tag(Optional<Integer> idTag, String name, String description, String color) {
        this.idTag = idTag;
        this.name = name;
        this.description = description;
        this.color = color;
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

    public String getColor() {
        return color;
    }
}
