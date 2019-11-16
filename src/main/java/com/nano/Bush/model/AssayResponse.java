package com.nano.Bush.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AssayResponse {
  private final Optional<Integer> idAssay;
  private final Optional<Timestamp> created;
  private  Optional<Timestamp> estimatedFinished;
  private final int idCrop;
  private final String name;
  private final String description;
  private final String user;
  private final List<Tag> tags;
  private final Crop crop;
  private final Set<Integer> idAgrochemicals;
  private final Set<Integer> idMixtures;
  private final Integer treatments;
  private final Integer experiments;
  private Optional<AssayStatesEnum> state;
  private  Optional<Integer> stars; //if assay finished
  private  Optional<String> comments; //if assay finished
  private Optional<Instant> finishedDate;

  public AssayResponse(Assay assay, Optional<Timestamp> estimatedFinished, List<Tag> tags, Crop crop, Set<Integer> idAgrochemicals, Set<Integer> idMixtures, Integer treatments, Integer experiments, String user, Optional<Integer> stars, Optional<String> comments, Optional<Instant> finishedDate) {
    this.idAssay = assay.getIdAssay();
    this.created = assay.getCreated();
    this.idCrop = assay.getIdCrop();
    this.state = assay.getState();
    this.name = assay.getName();
    this.description = assay.getDescription();
    this.estimatedFinished = estimatedFinished;
    this.user = user;
    this.tags = tags;
    this.crop = crop;
    this.idAgrochemicals = idAgrochemicals;
    this.idMixtures = idMixtures;
    this.treatments = treatments;
    this.experiments = experiments;
    this.stars = stars;
    this.comments = comments;
    this.finishedDate = finishedDate;
  }

  public Optional<Integer> getIdAssay() {
    return idAssay;
  }

  public int getIdCrop() {
    return idCrop;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getUser() {
    return user;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public Crop getCrop() {
    return crop;
  }

  public Set<Integer> getIdAgrochemicals() {
    return idAgrochemicals;
  }

  public Set<Integer> getIdMixtures() {
    return idMixtures;
  }

  public Optional<Timestamp> getCreated() {
    return created;
  }

  public Integer getTreatments() {
    return treatments;
  }

  public Integer getExperiments() {
    return experiments;
  }

  public Optional<AssayStatesEnum> getState() {
    return state;
  }

  public Optional<Timestamp> getEstimatedFinished() {
    return estimatedFinished;
  }

  public Optional<Integer> getStars() {
    return stars;
  }

  public Optional<String> getComments() {
    return comments;
  }

  public Optional<Instant> getFinishedDate() {
    return finishedDate;
  }
}
