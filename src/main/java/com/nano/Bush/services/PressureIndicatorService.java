package com.nano.Bush.services;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.model.PressureIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PressureIndicatorService {

    @Autowired
    AgrochemicalsDao agrochemicalsDao;

    public List<PressureIndicator> getPressureIndicatorInfoFor(Integer treatmentId) throws SQLException {

        return agrochemicalsDao.getAgrochemicalWithPressureFrom(treatmentId);

    }

}