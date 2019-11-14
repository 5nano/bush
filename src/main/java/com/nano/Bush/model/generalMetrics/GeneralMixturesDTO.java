package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class GeneralMixturesDTO {

    private List<String> labels;
    private List<String> parents;
    private List<Integer> values;
    private String branchValues;

    public GeneralMixturesDTO(List<Integer> values, List<String> labels, List<String> parents) {
        this.values = values;
        this.labels = labels;
        this.parents = parents;
        this.branchValues = "total";
    }

    public List<Integer> getValues() {
        return values;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<String> getParents() {
        return parents;
    }

    public String getBranchValues() {
        return branchValues;
    }
}
