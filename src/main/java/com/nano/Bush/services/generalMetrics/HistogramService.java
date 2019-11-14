package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.generalMetrics.HistogramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HistogramService {

    @Autowired
    MeasuresDao measuresDao;
    @Autowired
    ExperimentsDao experimentsDao;
    @Autowired
    AssaysDao assaysDao;

    public HistogramDTO getHistogramTest(Integer companyId) throws SQLException {

        List<Experiment> experiments = experimentsDao.getExperimentsByCompany(companyId);

        Map<String, List<String>> assayWithDate = new HashMap<>();

        List<Integer> assayIds = experiments.stream()
                .map(experiment -> experiment.getAssayId()).distinct().collect(Collectors.toList());

        for (Integer assayId : assayIds) {

            List<List<String>> dates = new ArrayList<>();

            List<Experiment> experimentsOfAssay = experiments.stream()
                    .filter(experiment -> experiment.getAssayId().equals(assayId))
                    .collect(Collectors.toList());

            experimentsOfAssay.forEach(experiment -> dates.add(getDatePicturesByAssayAndExperiment(experiment)));

            assayWithDate.put(assaysDao.getAssay(assayId).get().getName(), dates.stream().flatMap(List::stream).collect(Collectors.toList()));


        }

        return new HistogramDTO(assayWithDate);
    }

    private List<String> getDatePicturesByAssayAndExperiment(Experiment experiment) {
        return measuresDao.getDatePicturesByAssayAndExperiment(experiment.getAssayId(), experiment.getExperimentId().get());
    }
}
