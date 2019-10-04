package com.nano.Bush.model;

/**
 * Created by Matias Zeitune oct. 2019
 **/
public class AssayInsertResponse {
    private final Integer idAssay;

    public AssayInsertResponse(Integer idAssay) {
        this.idAssay = idAssay;
    }

    public Integer getIdAssay() {
        return idAssay;
    }
}
