package com.nano.Bush.model;

import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class Treatment {


    private final Optional<Integer> idTreatment;
    private Optional<Integer> experimentsLength;
    private final Integer idAssay;
    private final Integer idMixture;
    private final Integer idAgrochemical;
    private final String name;
    private final String description;
    private final Optional<Double> pressure;

    public Treatment(Optional<Integer> idTreatment, Optional<Integer> experimentsLength, Integer idAssay, Integer idMixture, Integer idAgrochemical, String name, String description, Optional<Double> pressure) {
        this.idTreatment = idTreatment;
        this.experimentsLength = experimentsLength;
        this.idAssay = idAssay;
        this.idMixture = idMixture;
        this.idAgrochemical = idAgrochemical;
        this.name = name;
        this.description = description;
        this.pressure = pressure;
    }

    public Optional<Integer> getExperimentsLength() {
        return this.experimentsLength;
    }


    public Integer getIdAssay() {
        return this.idAssay;
    }

    public Integer getIdMixture() {
        return idMixture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIdAgrochemical() {
        return idAgrochemical;
    }

    public Optional<Integer> getIdTreatment() {
        return idTreatment;
    }

    public Optional<Double> getPressure() {
        return pressure;
    }

    public void setExperimentsLength(Optional<Integer> experimentsLength) {
        this.experimentsLength = experimentsLength;
    }
}
