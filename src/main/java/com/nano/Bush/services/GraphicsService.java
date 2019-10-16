package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.measuresGraphics.DataPoint;
import com.nano.Bush.model.measuresGraphics.GraphicDto;
import com.nano.Bush.model.measuresGraphics.GraphicLineTime;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GraphicsService {

    @Autowired
    private MeasuresDao measuresDao;
    @Autowired
    private ExperimentService experimentService;

    private static List<List<DataPoint>> getDatapointsFrom(List<List<MeasurePlant>> measures) {

        List<List<DataPoint>> dataPoints = new ArrayList<>();

        List<DataPoint> dataPointList = new ArrayList<>();

        for (List<MeasurePlant> measurePlants : measures) {
            dataPointList.clear();
            measurePlants.forEach(mp -> dataPointList.add(new DataPoint(mp.getDay().toString(), mp.getArea().getValue(), mp.getImage())));
            dataPoints.add(dataPointList);
        }

        return dataPoints;

    }

    public List<GraphicDto> getComparativeExperimentsData(Integer assayId) {

        List<List<MeasurePlant>> measures = new ArrayList<>();

        getExperimentsAssaysMap(assayId).forEach((expId, assId) -> measures.add(measuresDao.selectMeasuresFrom(Integer.valueOf(assId), Integer.valueOf(expId))));

        List<GraphicDto> graphicDtos = new ArrayList<>();

        getDatapointsFrom(measures).forEach(m -> putGraphic(measures, graphicDtos));

        return graphicDtos;
    }

    private Double averageMeasurePlants(List<MeasurePlant> measurePlants){
        Integer listLength = measurePlants.size();
        return (measurePlants.stream().mapToDouble(o -> o.getArea().getValue()).sum())/listLength;
    }

    public List<GraphicDto> getComparativeTreatmentData(Integer treatmentId) {

        List<List<MeasurePlant>> measures = new ArrayList<>();

        getExperimentsTreatmentMap(treatmentId).forEach((expId, assId) -> measures.add(measuresDao.selectMeasuresFrom(Integer.valueOf(assId), Integer.valueOf(expId))));

        List<GraphicDto> graphicDtos = new ArrayList<>();

        getDatapointsFrom(measures).forEach(m -> putGraphic(measures, graphicDtos));

        return graphicDtos;
    }


    public List<GraphicLineTime> getComparativeTreatmentAveragedData(Integer treatmentId) {

        List<List<MeasurePlant>> measures = new ArrayList<>();

        getExperimentsTreatmentMap(treatmentId).forEach((expId, assId) -> measures.add(measuresDao.selectMeasuresFrom(Integer.valueOf(assId), Integer.valueOf(expId))));
        List<MeasurePlant> measuresFlatted = measures.stream().flatMap(List::stream).collect(Collectors.toList());
        Map<LocalDate, List<MeasurePlant>> measuresAgruppedByDay = measuresFlatted.stream().collect(Collectors.groupingBy(MeasurePlant::getDay));
        Map<LocalDate, Double> measuresAveraged = measuresAgruppedByDay.entrySet().stream().map(entry ->
                Tuple.of(entry.getKey(),
                        averageMeasurePlants(entry.getValue()))).collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));
        return measuresAveraged.entrySet().stream().map(entry -> new GraphicLineTime(treatmentId,entry.getKey(),entry.getValue())).collect(Collectors.toList());
    }


    private Map<String, String> getExperimentsAssaysMap(Integer assayId) {
        Map<String, String> assays = new HashMap<>();

        List<Experiment> experiments = experimentService.getExperimentsFromAssay(assayId);

        experiments.forEach(experiment -> assays.put(experiment.getExperimentId().get().toString(), assayId.toString()));
        return assays;
    }

    private Map<String, String> getExperimentsTreatmentMap(Integer treatmentId) {
        Map<String, String> treatments = new HashMap<>();

        List<Experiment> experiments = experimentService.getExperimentsFromTreatment(treatmentId.toString());

        experiments.forEach(experiment -> treatments.put(experiment.getExperimentId().get().toString(), experiment.getAssayId().toString()));
        return treatments;
    }

    private void putGraphic(List<List<MeasurePlant>> measures, List<GraphicDto> graphicDtos) {
        GraphicDto graphicInfo = new GraphicDto();
        getDatapointsFrom(measures).forEach(graphicInfo::setDataPoints);
        graphicDtos.add(graphicInfo);
    }

}

