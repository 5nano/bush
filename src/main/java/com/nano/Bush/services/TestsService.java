package com.nano.Bush.services;

import com.nano.Bush.datasources.MeasureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestsService {

    @Autowired
    private MeasureDao measureDao;

    public List<String> getBase64Image(String idPlant, String idTest) {

        return measureDao.selectBase64ImageFrom(idPlant, idTest);

    }

}
