package com.nano.Bush.services;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.datasources.MixturesDao;
import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.*;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@Service
public class TreatmentsService {

    private static final Logger logger = LoggerFactory.getLogger(TreatmentsService.class);

    @Autowired
    private TreatmentsDao treatmentsDao;
    @Autowired
    private ExperimentService experimentsService;
    @Autowired
    private MixturesDao mixturesDao;
    @Autowired
    private AgrochemicalsDao agrochemicalsDao;
    @Autowired
    private AssaysDao assaysDao;

    public TreatmentInsertResponse insert(Treatment treatment) throws SQLException {
        Map<String, String> experiments = new HashMap<>();
        treatmentsDao.insert(treatment).stream().forEach(experimentId -> {
            experiments.put(experimentId.toString(), treatment.getIdAssay().toString() + "-" + experimentId.toString());
        });
        return new TreatmentInsertResponse(experiments);
    }

    public void modify(Treatment treatment) throws SQLException {
        treatmentsDao.update(treatment);
    }

    public void delete(Integer idTreatment) throws SQLException {
        treatmentsDao.delete(idTreatment);
    }

    public List<Treatment> treatments(Integer idAssay) {
        List<Treatment> treatments = Try.of(()-> treatmentsDao.getTreatments(idAssay))
                .onFailure(error -> logger.error("Unexpected error",error))
                .getOrElse(Collections.emptyList());

        treatments.forEach(treatment -> {
            Integer experimentsLength = experimentsService.getExperimentsFromTreatment(treatment.getIdTreatment().get().toString()).size();
            treatment.setExperimentsLength(Optional.of(experimentsLength));
        });
        return treatments;
    }

    public List<TreatmentResponse> getTreatmentsFrom(Integer idAssay) {
        final List<Treatment> treatments = treatments(idAssay);
        return treatments.stream().map(treatment -> {
            final Optional<Mixture> mixture = mixturesDao.getMixture(treatment.getIdMixture());
            final Optional<Agrochemical> agrochemical = agrochemicalsDao.getAgrochemical(treatment.getIdAgrochemical());
            final String assay = assaysDao.getAssay(idAssay).map(a -> a.getName()).orElse("");
            return new TreatmentResponse(treatment,assay,mixture,agrochemical);
        }).collect(Collectors.toList());
    }

    public Treatment treatment(Integer idTreatment) throws SQLException {
        Integer experimentsLength = experimentsService.getExperimentsFromTreatment(idTreatment.toString()).size();
        Treatment treatment = treatmentsDao.getTreatment(idTreatment);
        treatment.setExperimentsLength(Optional.of(experimentsLength));
        return treatment;
    }

    public TreatmentInsertResponse getTreatmentQRs(Integer idTreatment) throws SQLException {
        Map<String, String> experiments = new HashMap<>();
        experimentsService.getExperimentsFromTreatment(idTreatment.toString()).stream().forEach(experiment -> {
            experiments.put(experiment.getExperimentId().get().toString(), experiment.getAssayId().toString() + "-" + experiment.getExperimentId().get().toString());
        });
        return new TreatmentInsertResponse(experiments);
    }


}
