package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class SankeyAssayDTO {
    private List<String> label;
    private List<Integer> source;
    private List<Integer> target;
    private List<Integer> value;

    public SankeyAssayDTO(List<String> label, List<Integer> source, List<Integer> target, List<Integer> value) {
        this.label = label;
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<Integer> getSource() {
        return source;
    }

    public void setSource(List<Integer> source) {
        this.source = source;
    }

    public List<Integer> getTarget() {
        return target;
    }

    public void setTarget(List<Integer> target) {
        this.target = target;
    }

    public List<Integer> getValue() {
        return value;
    }

    public void setValue(List<Integer> value) {
        this.value = value;
    }

}
