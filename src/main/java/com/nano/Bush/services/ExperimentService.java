package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.model.Experiment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ExperimentService {

    private static final Logger logger = LoggerFactory.getLogger(ExperimentService.class);

    public Experiment getExperimentNameFrom(String experimentId) throws SQLException {

        ExperimentsDao experimentsDao = new ExperimentsDao();

        Experiment experiment;

        try {
            experiment = experimentsDao.getExperiment(experimentId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return experiment;
    }
}
