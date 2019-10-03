package com.nano.Bush.model;

import java.util.Map;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class TreatmentInsertResponse {

    private final Map<String, String> experimentsQR;


    public TreatmentInsertResponse(Map<String, String> experimentsQR) {
        this.experimentsQR = experimentsQR;
    }

    public Map<String, String> getExperimentsQR(){
        return experimentsQR;
    }
}
