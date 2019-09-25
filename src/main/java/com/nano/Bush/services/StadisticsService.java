package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.BoxDiagramValues;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Matias Zeitune sep. 2019
 **/
@Service
public class StadisticsService {

    @Autowired
    private MeasuresDao measuresDao;

    private static final Logger logger = LoggerFactory.getLogger(StadisticsService.class);

    public BoxDiagramValues getBoxDiagramValuesExperiment(String experimentId) throws SQLException {
        ExperimentsDao experimentsDao = new ExperimentsDao();

        BoxDiagramValues boxDiagramValues;

        try {
            Experiment experiment;
            experiment = experimentsDao.getExperiment(experimentId);
            List<List<Double>> frequencies = measuresDao.selectMeasuresFrom(experiment.getAssayId().toString(), experimentId)
                    .stream()
                    .map(measure -> measure.getObservations().getYellowFrequencies().getValue())
                    .collect(Collectors.toList());
            List<Double> allFrequenciesExperiment = frequencies.stream().flatMap(List::stream).collect(Collectors.toList());
            Set<Double> yellowFrequencies = new HashSet<>(allFrequenciesExperiment);


            boxDiagramValues = new BoxDiagramValues(0.0, 0.0, 0.0, 0.0, 0.0);

        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return boxDiagramValues;
    }
}
