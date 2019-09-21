package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.MeasuresDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestsService {

    @Autowired
    private MeasuresDao measuresDao;

    public List<String> getBase64Image(String idPlant, String idTest) {

        return measuresDao.selectBase64ImageFrom(idPlant, idTest);

    }

}
