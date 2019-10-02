package com.nano.Bush.model;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class Treatment {

    private final Integer experimentsLength;
    private final Integer idAssay;
    private final Integer idMixture;
    private final String name;
    private final String description;

    public Treatment(Integer experimentsLength, Integer idAssay, Integer idMixture, String name, String description) {
        this.experimentsLength = experimentsLength;
        this.idAssay = idAssay;
        this.idMixture = idMixture;
        this.name = name;
        this.description = description;
    }

    public Integer getExperimentsLength(){
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
}
