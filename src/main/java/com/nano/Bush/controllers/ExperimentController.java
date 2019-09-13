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

    //TODO: hacer los ABMs de experimentos y pruebas (pruebas es solo mostrarlas)
    //TODO: hacer un enum con los campos por las dudas que cambien asi queda mas prolijo
    //TODO: cuando se borra un ensayo se borran todos sus experimentos asociados y pruebas(?)
    //TODO: cuando se borra un experimento se borran todas sus pruebas asociadas(?)

    @Autowired
    private ExperimentService experimentService;

    @RequestMapping(value = "/experimentos/nombre", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody
    String getExperimentName(@RequestParam String experimentId) throws SQLException {
        return experimentService.getExperimentNameFrom(experimentId).getName();
    }


}