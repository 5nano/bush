package com.nano.Bush.model;

import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class Treatment {

    private final Optional<Integer> idTreatment;
    private final Optional<Integer> experimentsLength;
    private final Integer idAssay;
    private final Integer idMixture;
    private final Integer idAgrochemical;
    private final String name;
    private final String description;

    public Treatment(Optional<Integer> idTreatment, Optional<Integer> experimentsLength, Integer idAssay, Integer idMixture, Integer idAgrochemical, String name, String description) {
        this.idTreatment = idTreatment;
        this.experimentsLength = experimentsLength;
        this.idAssay = idAssay;
        this.idMixture = idMixture;
        this.idAgrochemical = idAgrochemical;
        this.name = name;
        this.description = description;
    }

    public Optional<Integer> getExperimentsLength(){
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
}
