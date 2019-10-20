package com.nano.Bush.model.stadistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Matias Zeitune sep. 2019
 **/


/*
*
* [
{date: '11-10-2009', values: [{treatmentId: 1, values: [....]}, {treatmentId: 2, values: [....]}, {treatmentId: 3, values: [....]}]}
{date: '12-10-2009', values: [{treatmentId: 1, values: [....]}, {treatmentId: 2, values: [....]}, {treatmentId: 3, values: [....]}]}
]
* */
public class BoxDiagramaByExperiment {
    private final LocalDate date;
    private final List<BoxDiagramDto> values;

    public BoxDiagramaByExperiment(LocalDate date, List<BoxDiagramDto> values) {
        this.date = date;
        this.values = values;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<BoxDiagramDto> getValues() {
        return values;
    }
}
