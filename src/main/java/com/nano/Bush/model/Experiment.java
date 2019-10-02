package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Experiment {

    private final Integer assayId;
    private final Integer mixtureId;
    private final Integer experimentId;
    @JsonProperty("nombre")
    private String name;
    @JsonProperty("descripcion")
    private String description;

    public Experiment(String name, String description, Integer assayId, Integer mixtureId, Integer experimentId) {
        this.name = name;
        this.description = description;
        this.assayId = assayId;
        this.mixtureId = mixtureId;
        this.experimentId = experimentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAssayId() {
        return assayId;
    }

    public Integer getMixtureId() {
        return mixtureId;
    }

    public Integer getExperimentId() {
        return experimentId;
    }
}
