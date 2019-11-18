package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class GeneralMixturesDTO {

    private List<String> ids;
    private List<String> labels;
    private List<String> parents;

    public GeneralMixturesDTO(List<String> ids, List<String> labels, List<String> parents) {
        this.ids = ids;
        this.labels = labels;
        this.parents = parents;
    }

    public List<String> getParents() {
        return parents;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<String> getIds() {
        return ids;
    }
}
