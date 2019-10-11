package com.nano.Bush.model;

import java.util.List;
import java.util.Optional;

public class AssayResponse {
    private final Optional<Integer> idAssay;
    private final int idCrop;
    private final String name;
    private final String description;
    private final int idUserCreator;
    private final List<Tag> tags;
    private final Crop crop;
    private final Agrochemical agrochemical;
    private final Mixture mixture;

  public AssayResponse(Assay assay, List<Tag> tags, Crop crop, Agrochemical agrochemical, Mixture mixture) {
    this.idAssay = assay.getIdAssay();
    this.idCrop = assay.getIdCrop();
    this.name = assay.getName();
    this.description = assay.getDescription();
    this.idUserCreator = assay.getIdUserCreator();
    this.tags = tags;
    this.crop = crop;
    this.agrochemical = agrochemical;
    this.mixture = mixture;
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

  public int getIdUserCreator() {
    return idUserCreator;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public Crop getCrop() {
    return crop;
  }

  public Agrochemical getAgrochemical() {
    return agrochemical;
  }

  public Mixture getMixture() {
    return mixture;
  }
}
