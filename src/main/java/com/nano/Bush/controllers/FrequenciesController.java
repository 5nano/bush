package com.nano.Bush.controllers;

import com.nano.Bush.model.BoxDiagramDto;
import com.nano.Bush.services.StadisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
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
    ResponseEntity<BoxDiagramDto> getYellowFrequencies(@RequestParam String experimentId) throws SQLException {
        return new ResponseEntity<>(stadisticsService.getYellowFrequenciesValuesExperiment(experimentId), HttpStatus.OK);
    }

}
