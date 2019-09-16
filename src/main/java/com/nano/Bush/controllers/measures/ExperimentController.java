package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.ExperimentService;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ExperimentController {

    //TODO: hacer el get de pruebas
    //TODO: hacer un enum con los campos por las dudas que cambien asi queda mas prolijo
    //TODO: definir que pasa cuando se borra un ensayo o un experimento o algo que tenga foreign key

    @Autowired
    private ExperimentService experimentService;

    @RequestMapping(value = "/experimentos/nombre", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody
    String getExperimentName(@RequestParam String experimentId) throws SQLException {
        return experimentService.getExperimentNameFrom(experimentId).getName();
    }

    @RequestMapping(value = "/experimentos/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> insertExperiment(@RequestBody Experiment experiment) throws SQLException {

        ExperimentsDao experimentsDao = new ExperimentsDao();
        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("nombre", "experimento", experiment.getName())) {
            return new ResponseEntity<>(new Response("El nombre del experimento ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            experimentsDao.insert(experiment);
            return new ResponseEntity<>(new Response("Experimento Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/experimentos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Experiment>> showExperiments() throws SQLException {
        ExperimentsDao experimentsDao = new ExperimentsDao();

        return new ResponseEntity<>(experimentsDao.getExperiments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/experimentos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteExperiment(@RequestBody Experiment experiment) throws SQLException {

        ExperimentsDao experimentsDao = new ExperimentsDao();
        ValidationsService validationsService = new ValidationsService();

        if (!validationsService.isRepetead("nombre", "experimento", experiment.getName())) {
            return new ResponseEntity<>(new Response("El experimento a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            experimentsDao.delete(experiment.getName());
            return new ResponseEntity<>(new Response("Experimento Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

}