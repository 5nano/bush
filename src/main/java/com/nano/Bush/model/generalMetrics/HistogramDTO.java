package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class HistogramDTO {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

    public HistogramDTO(List<String> dates) {
        this.dates = dates;
    }
}
