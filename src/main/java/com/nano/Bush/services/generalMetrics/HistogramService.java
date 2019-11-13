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
                    if (isEmptyDate(experiment)) {
                        dateForPictures.add(getDatePicturesByAssayAndExperiment(experiment));
                    }
                }
        );

        return new HistogramDTO(dateForPictures);
    }

    private boolean isEmptyDate(Experiment experiment) {
        return !getDatePicturesByAssayAndExperiment(experiment).equals("");
    }

    private String getDatePicturesByAssayAndExperiment(Experiment experiment) {
        return measuresDao.getDatePicturesByAssayAndExperiment(experiment.getAssayId(), experiment.getExperimentId().get());
    }
}
