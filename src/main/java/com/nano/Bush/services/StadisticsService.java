package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import com.nano.Bush.model.stadistic.BoxDiagramDto;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
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
    private TreatmentsDao treatmentsDao;


    public Map<LocalDate, Map<Integer, List<Double>>> getYellowFrequenciesValuesAssay(Integer assayId) throws SQLException {
        return getFrequenciesValuesAssay(assayId, "yellow");
    }

    public Map<LocalDate, Map<Integer, List<Double>>> getGreenFrequenciesValuesAssay(Integer assayId) throws SQLException {
        return getFrequenciesValuesAssay(assayId, "green");
    }


    public Map<LocalDate, Map<Integer, List<Double>>> getAreaValuesAssay(Integer assayId) throws SQLException {
        return getFrequenciesValuesAssay(assayId, "area");
    }

    private List<GroupMedian> allMediansResults(Integer assayId) throws SQLException {
        List<Treatment> treatments;
        treatments = treatmentsDao.getTreatments(assayId);
        return treatments.stream()
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
    }

    private List<Double> makeByOperation(GroupMedian groupMedian, String typeValue) {
        if (typeValue.equals("yellow")) {
            return groupMedian.measurePlant.getYellowFrequencies().getValue();
        } else if (typeValue.equals("green")) {
            return groupMedian.measurePlant.getGreenFrequencies().getValue();
        } else {
            List<Double> area = new LinkedList<>();
            area.add(groupMedian.measurePlant.getArea().getValue());
            return area;
        }
    }

    private Map<LocalDate, List<BoxDiagramDto>> getValues(Map<LocalDate, List<GroupMedian>> medians, String typeValue) {

        return medians.entrySet()
                .stream()
                .map(entry -> Tuple.of(entry.getKey(), entry.getValue().stream()
                        .map(groupMedian ->
                                new BoxDiagramDto(groupMedian.treatmentId,
                                        this.makeByOperation(groupMedian, typeValue)
                                                .stream().filter(frequencie -> frequencie != 0).collect(Collectors.toList())))
                        .collect(Collectors.toList())))
                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
    }

    private Map<LocalDate, Map<Integer, List<Double>>> getFrequenciesValuesAssay(Integer assayId, String typeValue) throws SQLException {
        logger.info("Getting frequencies values assay {}, type {}",assayId, typeValue);
        List<GroupMedian> collectMedians = this.allMediansResults(assayId);
        Map<LocalDate, List<GroupMedian>> medians = collectMedians.stream().collect(Collectors.groupingBy(GroupMedian::getDate));
        Map<LocalDate, List<BoxDiagramDto>> collectedByday = this.getValues(medians, typeValue);
        Map<LocalDate, Map<Integer, List<BoxDiagramDto>>> collectByDateAndCollectedByTreatment = collectedByday.entrySet().stream().map(entry ->
                Tuple.of(entry.getKey(),
                        entry.getValue().stream().collect(Collectors.groupingBy(BoxDiagramDto::getTreatmentId))))
                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

        List<Tuple2<LocalDate, Map<Integer, List<Double>>>> collect1 = collectByDateAndCollectedByTreatment.entrySet().stream().map(entry ->
                Tuple.of(entry.getKey(),
                        entry.getValue().entrySet()
                                .stream()
                                .map(entry2 -> {
                                    List<Double> values = entry2.getValue().stream()
                                            .map(dto -> dto.getValues())
                                            .flatMap(List::stream)
                                            .collect(Collectors.toList());
                                    Collections.sort(values, Comparator.comparingDouble(Double::doubleValue));
                                    Integer listSize = values.size();
                                    values.subList((int)(listSize * 0.6),listSize).clear(); //TODO: sacar estas dos ultimas lineas en caso de no querer sacar valores
                                    return Tuple.of(entry2.getKey(), values);
                                        })
                                .collect(Collectors.toMap(Tuple2::_1, Tuple2::_2))
                ))
                .collect(Collectors.toList());

        Map<LocalDate, Map<Integer, List<Double>>> collect = collect1.stream().collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
        return new TreeMap<LocalDate, Map<Integer, List<Double>>>(collect);

    }

    private static class GroupMedian {
        LocalDate date;
        MeasurePlant measurePlant;
        Integer treatmentId;

        public GroupMedian(LocalDate date, MeasurePlant measurePlant, Integer treatmentId) {
            this.date = date;
            this.measurePlant = measurePlant;
            this.treatmentId = treatmentId;
        }

        public LocalDate getDate() {
            return date;
        }

        public MeasurePlant getMeasurePlant() {
            return measurePlant;
        }

        public Integer getTreatmentId() {
            return treatmentId;
        }
    }
}

