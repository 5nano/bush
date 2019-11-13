package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.generalMetrics.HistogramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HistogramService {

    @Autowired
    MeasuresDao measuresDao;
    @Autowired
    ExperimentsDao experimentsDao;

    public HistogramDTO getHistogramTest(Integer companyId) throws SQLException {

        List<Experiment> experiments = experimentsDao.getExperimentsByCompany(companyId);
        List<String> dateForPictures = new ArrayList<>();
        experiments.forEach(experiment -> {
                    if (!measuresDao.getDatePicturesByAssayAndExperiment(experiment.getAssayId(), experiment.getExperimentId().get()).equals("")) {
                        dateForPictures.add(measuresDao.getDatePicturesByAssayAndExperiment(experiment.getAssayId(), experiment.getExperimentId().get()));
                    }
                }
        );

        //TODO: ver porque trae vacío siempre
        Map<String, Long> countPicturesByDay = dateForPictures
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> countPicturesByDaySorted = new TreeMap<>(countPicturesByDay);

        List<String> count = countPicturesByDaySorted.values()
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new HistogramDTO(new ArrayList<>(countPicturesByDaySorted.keySet()), count);
    }
}
