package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Experiment {

    public Experiment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonProperty("nombre")
    private String name;
    @JsonProperty("descripcion")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
