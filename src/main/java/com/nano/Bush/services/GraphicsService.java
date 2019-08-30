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

        List<MeasurePlant> measures = measureDao.selectMeasuresFrom("1", "1");//TODO: poner los IDS del postgres para buscar

        GraphicDto graphicInfo = new GraphicDto();
        List<DataPoint> dataPoints = new ArrayList<>();

        measures.forEach(mp -> dataPoints.add(new DataPoint(mp.getDay().toString(), mp.getObservations().getArea().getValue())));

        graphicInfo.setDataPoints(dataPoints);

        List<GraphicDto> graphicDtos = new ArrayList<>();
        graphicDtos.add(graphicInfo);
        return graphicDtos;
    }

}

