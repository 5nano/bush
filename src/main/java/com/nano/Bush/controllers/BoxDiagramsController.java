package com.nano.Bush.controllers;

import com.nano.Bush.model.stadistic.BoxDiagramDto;
import com.nano.Bush.model.stadistic.BoxDiagramaByExperiment;
import com.nano.Bush.services.StadisticsService;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Zeitune sep. 2019
 **/
@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class BoxDiagramsController {


    @Autowired
    private StadisticsService stadisticsService;

    @RequestMapping(value = "/frecuencias/yellow", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Map<LocalDate, Map<Integer, List<Double>>>> getYellowFrequencies(@RequestParam Integer assayId) throws SQLException {
        return new ResponseEntity<>(stadisticsService.getYellowFrequenciesValuesAssay(assayId), HttpStatus.OK);
    }

    @RequestMapping(value = "/frecuencias/green", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Map<LocalDate, Map<Integer, List<Double>>>> getGreenFrequencies(@RequestParam Integer assayId) throws SQLException {
        return new ResponseEntity<>(stadisticsService.getGreenFrequenciesValuesAssay(assayId), HttpStatus.OK);
    }

}
