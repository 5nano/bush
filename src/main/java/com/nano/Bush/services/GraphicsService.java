package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.MeasuresDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.measuresGraphics.DataPoint;
import com.nano.Bush.model.measuresGraphics.GraphicDto;
import com.nano.Bush.model.measuresGraphics.MeasurePlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            measurePlants.forEach(mp -> dataPointList.add(new DataPoint(mp.getDay().toString(), mp.getArea().getValue())));
            dataPoints.add(dataPointList);
        }

        return dataPoints;

    }

    public List<GraphicDto> getComparativeExperimentsData(String assayId) {

        List<List<MeasurePlant>> measures = new ArrayList<>();

        getExperimentsAssaysMap(assayId).forEach((expId, assId) -> measures.add(measuresDao.selectMeasuresFrom(assId, expId)));

        List<GraphicDto> graphicDtos = new ArrayList<>();

        getDatapointsFrom(measures).forEach(m -> putGraphic(measures, graphicDtos));

        return graphicDtos;
    }

    private Map<String, String> getExperimentsAssaysMap(String assayId) {
        Map<String, String> assays = new HashMap<>();

        List<Experiment> experiments = experimentService.getExperimentsFromAssay(assayId);

        experiments.forEach(experiment -> assays.put(experiment.getExperimentId().get().toString(), assayId));
        return assays;
    }

    private void putGraphic(List<List<MeasurePlant>> measures, List<GraphicDto> graphicDtos) {
        GraphicDto graphicInfo = new GraphicDto();
        getDatapointsFrom(measures).forEach(graphicInfo::setDataPoints);
        graphicDtos.add(graphicInfo);
    }

}

