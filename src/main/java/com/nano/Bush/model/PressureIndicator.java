package com.nano.Bush.model;

import io.vavr.Tuple2;

public class PressureIndicator {

    Integer mixtureId;
    Float value;
    String text;
    Tuple2<Integer, Integer> range;

    public PressureIndicator(Float value, String text, Tuple2<Integer, Integer> range, Integer mixtureId) {
        this.value = value;
        this.text = text;
        this.range = range;
        this.mixtureId = mixtureId;
    }

    public Float getValue() {
        return value;
    }

}