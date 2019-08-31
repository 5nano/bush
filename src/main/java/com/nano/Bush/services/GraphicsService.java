package com.nano.Bush.services;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.GraphicDao;
import com.nano.Bush.datasources.MeasureDao;
import com.nano.Bush.model.DataPoint;
import com.nano.Bush.model.GraphicDto;
import com.nano.Bush.model.MeasurePlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GraphicsService {

    @Autowired
    private MeasureDao measureDao;

    public List<GraphicDto> getComparativeGraphicInfo(String crop) {
        PostgresConnector postgresConnector = new PostgresConnector();
        GraphicDao graphicDao = new GraphicDao(postgresConnector.getConnection());

        return getDto(graphicDao.getExperimentsIds(crop));
    }

    private List<GraphicDto> getDto(Map<String, String> tests) {

        tests.put("1", "2");//TODO: poner los IDS del postgres para buscar
        tests.put("2", "2");
        List<List<MeasurePlant>> measures = new ArrayList<>();

        tests.forEach((expId, assId) -> measures.add(measureDao.selectMeasuresFrom(assId, expId)));

        List<GraphicDto> graphicDtos = new ArrayList<>();

        getDatapointsFrom(measures).forEach(m -> {
            GraphicDto graphicInfo = new GraphicDto();
            getDatapointsFrom(measures).forEach(graphicInfo::setDataPoints);
            graphicDtos.add(graphicInfo);
        });

        return graphicDtos;
    }

    private static List<List<DataPoint>> getDatapointsFrom(List<List<MeasurePlant>> measures) {

        List<List<DataPoint>> dataPoints = new ArrayList<>();

        List<DataPoint> dataPointList = new ArrayList<>();

        for (List<MeasurePlant> measurePlants : measures) {
            dataPointList.clear();
            measurePlants.forEach(mp -> dataPointList.add(new DataPoint(mp.getDay().toString(), mp.getObservations().getArea().getValue())));
            dataPoints.add(dataPointList);
        }

        return dataPoints;

    }

}

