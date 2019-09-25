package com.nano.Bush.model.measuresGraphics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Zeitune sep. 2019
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Frequencies {

    @JsonProperty("value")
    private List<Double> value = new ArrayList<Double>();

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}
