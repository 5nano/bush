package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.stadistic.ExperimentPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperimentService {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentService.class);

    @Autowired
    ExperimentsDao experimentsDao;
    @Autowired
    MeasuresDao measuresDao;

    @Autowired
    AssaysDao assaysDao;

    public ExperimentPoint getExperimentPoint(Integer experimentId, String timestamp) throws SQLException {
        List<ExperimentPoint> experimentPoints = this.getExperimentPoints(experimentId);

        Instant inst = Instant.parse(timestamp);
        return experimentPoints.stream().filter(experimentPoint -> experimentPoint.getInstant().equals(inst)).collect(Collectors.toList()).get(0);
    }

    public List<ExperimentPoint> getExperimentPoints(Integer experimentId) throws SQLException {

        Experiment experiment = experimentsDao.getExperiment(experimentId);
        return measuresDao.selectMeasuresFrom(experiment.getAssayId(), experimentId)
                .stream()
                .map(measurePlant ->
                        new ExperimentPoint(
                                experiment.getAssayId(),
                                experiment.getTreatmentId(),
                                experimentId,
                                measurePlant.getImage(),
                                measurePlant.getDay(),
                                measurePlant.getDayWithHour(),
                                measurePlant.getWidth().getValue(),
                                measurePlant.getHeight().getValue(),
                                measurePlant.getArea().getValue()
                        )
                ).collect(Collectors.toList());

    }

    public Experiment getExperimentNameFrom(Integer experimentId) {

        Experiment experiment;

        try {
            experiment = experimentsDao.getExperiment(experimentId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiment;
    }

    public List<Experiment> getExperimentsFromAssay(Integer assayId) {

        List<Experiment> experiments;

        try {
            experiments = assaysDao.getExperimentsFromAssay(assayId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiments;
    }

    public List<Experiment> getExperimentsFromTreatment(String treatmentId) {

        List<Experiment> experiments;

        try {
            experiments = assaysDao.getExperimentsFromTreatment(treatmentId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiments;
    }
}
