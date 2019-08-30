package com.nano.Bush.services;

import com.nano.Bush.datasources.MeasureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestsService {


    @Autowired
    private MeasureDao measureDao;

    public String getComparativeGraphicInfo(String idPlant, String idTest) {

        return measureDao.selectBase64ImageFrom(idPlant, idTest);

    }

}
