package com.nano.Bush.controllers;

import com.nano.Bush.services.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ExperimentController {


    @Autowired
    private ExperimentService experimentService;

    @RequestMapping(value = "/experimentos/nombre", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody
    String getAssayName(@RequestParam String experimentId) throws SQLException {
        return experimentService.getExperimentNameFrom(experimentId).getName();
    }
}