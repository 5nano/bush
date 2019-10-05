package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.stadistic.BoxDiagramaByExperiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Matias Zeitune sep. 2019
 **/
@Service
public class StadisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StadisticsService.class);
    @Autowired
    private MeasuresDao measuresDao;
    @Autowired
    private AssaysDao assaysDao;
    @Autowired
    private ExperimentsDao experimentsDao;

    public Set<Double> getYellowFrequenciesValuesExperiment(String experimentId) throws SQLException {
        Set<Double> yellowFrequencies;

        try {
            Experiment experiment;
            experiment = experimentsDao.getExperiment(experimentId);
            List<List<Double>> frequencies = measuresDao.selectMeasuresFrom(experiment.getAssayId().toString(), experimentId)
                    .stream()
                    .map(measure -> measure.getYellowFrequencies().getValue())
                    .collect(Collectors.toList());
            List<Double> allFrequenciesExperiment = frequencies.stream().flatMap(List::stream).collect(Collectors.toList());
            yellowFrequencies = new HashSet<>(allFrequenciesExperiment);
            Set<Double> sorted = new TreeSet<Double>(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return o2.compareTo(o1);
                }
            });
            sorted.addAll(yellowFrequencies);
            yellowFrequencies = sorted;
            yellowFrequencies.remove(new Double(0));
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }

        return yellowFrequencies;
    }

    public List<BoxDiagramaByExperiment> getYellowFrequenciesValuesAssay(String assayId) throws SQLException {
        List<BoxDiagramaByExperiment> yellowFrequencies;

        try {
            List<Experiment> experiments;
            experiments = assaysDao.getExperimentsFromAssay(assayId);
            List<BoxDiagramaByExperiment> frequenciesByExperiment = experiments.stream()
                    .map(experiment -> {
                        try {
                            return new BoxDiagramaByExperiment(experiment.getExperimentId().get(), experiment.getTreatmentId(), this.getYellowFrequenciesValuesExperiment(experiment.getExperimentId().get().toString()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .collect(Collectors.toList());
            yellowFrequencies = frequenciesByExperiment;

        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }

        return yellowFrequencies;
    }
}
