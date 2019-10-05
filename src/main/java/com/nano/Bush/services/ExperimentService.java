package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperimentService {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentService.class);

    @Autowired
    ExperimentsDao experimentsDao;

    @Autowired
    AssaysDao assaysDao;

    public Experiment getExperimentNameFrom(String experimentId) {

        Experiment experiment;

        try {
            experiment = experimentsDao.getExperiment(experimentId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiment;
    }

    public List<Experiment> getExperimentsFromAssay(String assayId) {

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
            experiments = assaysDao.getExperimentsFromAssay(treatmentId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiments;
    }
}
