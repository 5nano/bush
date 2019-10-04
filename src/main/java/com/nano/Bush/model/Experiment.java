package com.nano.Bush.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Experiment {

    private final Integer assayId;
    private final Integer treatmentId;
    private final Optional<Integer> experimentId;
    @JsonProperty("nombre")
    private String name;
    @JsonProperty("descripcion")
    private String description;

    public Experiment(String name, String description, Integer assayId, Integer treatmentId, Optional<Integer> experimentId) {
        this.name = name;
        this.description = description;
        this.assayId = assayId;
        this.treatmentId = treatmentId;
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

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public Optional<Integer> getExperimentId() {
        return experimentId;
    }
}
