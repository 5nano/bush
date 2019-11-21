package com.nano.Bush.model;

/**
 * Created by Matias Zeitune nov. 2019
 **/
public class AssayMinimalInfo {
    private Integer photosLength;
    private Integer experimentsLength;
    private Integer treatmentsLength;

    public AssayMinimalInfo(Integer photosLength, Integer experimentsLength, Integer treatmentsLength) {
        this.photosLength = photosLength;
        this.experimentsLength = experimentsLength;
        this.treatmentsLength = treatmentsLength;
    }

    public Integer getPhotosLength() {
        return photosLength;
    }

    public void setPhotosLength(Integer photosLength) {
        this.photosLength = photosLength;
    }

    public Integer getExperimentsLength() {
        return experimentsLength;
    }

    public void setExperimentsLength(Integer experimentsLength) {
        this.experimentsLength = experimentsLength;
    }

    public Integer getTreatmentsLength() {
        return treatmentsLength;
    }

    public void setTreatmentsLength(Integer treatmentsLength) {
        this.treatmentsLength = treatmentsLength;
    }
}
