package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class HistogramDTO {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

    public List<String> getCount() {
        return count;
    }

    private List<String> count;

    public HistogramDTO(List<String> dates, List<String> count) {
        this.dates = dates;
        this.count = count;
    }
}
