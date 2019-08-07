package com.nano.Bush.services;

import com.nano.Bush.datasources.GraphicsDao;
import com.nano.Bush.model.GraphicDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphicsService {

    @Autowired
    GraphicsDao graphicsDao;

    public List<GraphicDto> getComparativeGraphicInfo() {
        return graphicsDao.getTestsInfo();

    }

    /*private GraphicDto getInfo(List<DataPoint> tests) {

        GraphicDto graphicInfo = new GraphicDto();
        DataPoint dataPoint = new DataPoint();
        List<DataPoint> dataPoints = new ArrayList<>();
        dataPoint.setLabel(tests);
        dataPoints.add(dataPoint);
        graphicInfo.setDataPoints(dataPoints);

        return graphicInfo;
    }*/

}

