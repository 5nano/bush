package com.nano.Bush.model;

import java.util.Optional;

public class TreatmentResponse {
  private final Optional<Integer> idTreatment;
  private Optional<Integer> experimentsLength;
  private final String assay;
  private final Optional<Mixture> mixture;
  private final Optional<Agrochemical> agrochemical;
  private final String name;
  private final String description;
  private final Optional<Double> pressure;

  public TreatmentResponse(Treatment treatment, String assay, Optional<Mixture> mixture, Optional<Agrochemical> agrochemical) {
    this.idTreatment = treatment.getIdTreatment();
    this.experimentsLength = treatment.getExperimentsLength();
    this.assay = assay;
    this.mixture = mixture;
    this.agrochemical = agrochemical;
    this.name = treatment.getName();
    this.description = treatment.getDescription();
    this.pressure = treatment.getPressure();
  }

  public Optional<Integer> getExperimentsLength() {
    return this.experimentsLength;
  }


  public String getAssay() {
    return this.assay;
  }

  public Optional<Mixture> getMixture() {
    return mixture;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Optional<Agrochemical> getAgrochemical() {
    return agrochemical;
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
