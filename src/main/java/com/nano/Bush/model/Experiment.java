package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Experiment {

    @JsonProperty("nombre")
    private String name;
    @JsonProperty("descripcion")
    private String description;
    private Integer assayId;
    private Integer cropId;

    public Experiment(String name, String description, Integer assayId, Integer cropId) {
        this.name = name;
        this.description = description;
        this.assayId = assayId;
        this.cropId = cropId;
    }

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

    public Integer getAssayId() {
        return assayId;
    }

    public Integer getCropId() {
        return cropId;
    }
}
