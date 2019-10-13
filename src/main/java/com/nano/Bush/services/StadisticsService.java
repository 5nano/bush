package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import com.nano.Bush.model.stadistic.BoxDiagramDto;
import com.nano.Bush.model.stadistic.BoxDiagramaByExperiment;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.CollectionCertStoreParameters;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private TreatmentsDao treatmentsDao;


    public  Map<LocalDate, Map<Integer, List<Double>>> getYellowFrequenciesValuesAssay(Integer assayId) throws SQLException {
        return getFrequenciesValuesAssay(assayId,"yellow");
    }

    public  Map<LocalDate, Map<Integer, List<Double>>> getGreenFrequenciesValuesAssay(Integer assayId) throws SQLException {
        return getFrequenciesValuesAssay(assayId,"green");
    }


    public  Map<LocalDate, Map<Integer, List<Double>>> getFrequenciesValuesAssay(Integer assayId, String color) throws SQLException {

            List<Treatment> treatments;
            treatments = treatmentsDao.getTreatments(assayId);
            List<GroupMedian> collectMedians = treatments.stream()
                .map(treatment -> {
                    try {
                        List<Experiment> experiments = treatmentsDao.getExperiments(treatment.getIdTreatment().get());
                        List<MeasurePlant> measurePlants = experiments.stream().map(experiment ->
                                measuresDao.selectMeasuresFrom(treatment.getIdAssay(), experiment.getExperimentId().get()))
                                .flatMap(List::stream)
                                .collect(Collectors.toList());

                        return measurePlants.stream().map(measurePlant -> {
                            return new GroupMedian(measurePlant.getDay(), measurePlant, treatment.getIdTreatment().get());
                        }).collect(Collectors.toList());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).flatMap(List::stream).collect(Collectors.toList());

            Map<LocalDate, List<GroupMedian>> collect = collectMedians.stream().collect(Collectors.groupingBy(GroupMedian::getDate));
            Map<LocalDate, List<BoxDiagramDto>> collectedByday = collect.entrySet()
                .stream()
                .map(entry -> Tuple.of(entry.getKey(), entry.getValue().stream()
                        .map(groupMedian ->
                                new BoxDiagramDto(groupMedian.treatmentId, (color.equals("yellow") ? groupMedian.measurePlant.getYellowFrequencies() : groupMedian.measurePlant.getGreenFrequencies())
                                                .getValue().stream().filter(frequencie -> frequencie != 0).collect(Collectors.toList()))).collect(Collectors.toList())))
                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
             Map<LocalDate, Map<Integer, List<BoxDiagramDto>>> collectByDateAndCollectedByTreatment = collectedByday.entrySet().stream().map(entry ->
                Tuple.of(entry.getKey(),
                        entry.getValue().stream().collect(Collectors.groupingBy(BoxDiagramDto::getTreatmentId))))
                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

        List<Tuple2<LocalDate, Map<Integer, List<Double>>>> collect1 = collectByDateAndCollectedByTreatment.entrySet().stream().map(entry ->
                Tuple.of(entry.getKey(),
                        entry.getValue().entrySet()
                                .stream()
                                .map(entry2 ->
                                        Tuple.of(entry2.getKey(), entry2.getValue().stream()
                                                .map(dto -> dto.getValues())
                                                .flatMap(List::stream)
                                                .collect(Collectors.toList())))
                                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2))
                ))
                .collect(Collectors.toList());

        return collect1.stream().collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

    }

    private static class GroupMedian{
        LocalDate date;
        MeasurePlant measurePlant;
        Integer treatmentId;

        public LocalDate getDate() {
            return date;
        }

        public MeasurePlant getMeasurePlant() {
            return measurePlant;
        }

        public Integer getTreatmentId() {
            return treatmentId;
        }

        public GroupMedian(LocalDate date, MeasurePlant measurePlant, Integer treatmentId) {
            this.date = date;
            this.measurePlant = measurePlant;
            this.treatmentId = treatmentId;
        }
    }
}

