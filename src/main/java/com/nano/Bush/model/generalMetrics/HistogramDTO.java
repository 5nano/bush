package com.nano.Bush.model.generalMetrics;

import java.util.List;
import java.util.Map;

public class HistogramDTO {

    private Map<String, List<String>> dates;

    public Map<String, List<String>> getDates() {
        return dates;
    }

    public HistogramDTO(Map<String, List<String>> dates) {
        this.dates = dates;
    }
}
