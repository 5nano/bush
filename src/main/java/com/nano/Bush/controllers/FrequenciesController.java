package com.nano.Bush.controllers;

import com.nano.Bush.services.StadisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Matias Zeitune sep. 2019
 **/
@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class FrequenciesController {


    @Autowired
    private StadisticsService stadisticsService;

    @RequestMapping(value = "/frecuencias/yellow", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Set<Double> getYellowFrequencies(@RequestParam String experimentId) throws SQLException {
        return stadisticsService.getYellowFrequenciesValuesExperiment(experimentId);
    }
}
