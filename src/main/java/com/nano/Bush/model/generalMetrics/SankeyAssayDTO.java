package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class SankeyAssayDTO {
    private List<String> labels;
    private List<Integer> sources;
    private List<Integer> targets;
    private List<Integer> values;

    public SankeyAssayDTO(List<String> labels, List<Integer> sources, List<Integer> targets, List<Integer> values) {
        this.labels = labels;
        this.sources = sources;
        this.targets = targets;
        this.values = values;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Integer> getSources() {
        return sources;
    }

    public void setSources(List<Integer> sources) {
        this.sources = sources;
    }

    public List<Integer> getTargets() {
        return targets;
    }

    public void setTargets(List<Integer> targets) {
        this.targets = targets;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
