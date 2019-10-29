package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.ExperimentsDao;
import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Response;
import com.nano.Bush.model.stadistic.ExperimentPoint;
import com.nano.Bush.services.ExperimentService;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class ExperimentController {

    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ExperimentsDao experimentsDao;
    @Autowired
    private ValidationsService validationsService;


    @RequestMapping(value = "/experiment/points", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ExperimentPoint>> showExperimentPoints(@RequestParam Integer experimentId) throws SQLException {
        return new ResponseEntity<>(experimentService.getExperimentPoints(experimentId), HttpStatus.OK);
    }


    @RequestMapping(value = "/experiment/point", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ExperimentPoint> showExperimentPoint(@RequestParam Integer experimentId, LocalDate timestamp) throws SQLException {
        return new ResponseEntity<>(experimentService.getExperimentPoint(experimentId, timestamp), HttpStatus.OK);
    }

    @RequestMapping(value = "/experimentos/nombre", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody
    String getExperimentName(@RequestParam Integer experimentId) {
        return experimentService.getExperimentNameFrom(experimentId).getName();
    }


    @RequestMapping(value = "/experimentos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Experiment>> showExperimentsFrom(@RequestParam Integer assayId) {
        return new ResponseEntity<>(experimentService.getExperimentsFromAssay(assayId), HttpStatus.OK);
    }

    @RequestMapping(value = "/experimentos/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> insertExperiment(@RequestBody Experiment experiment) throws SQLException {
        experimentsDao.insert(experiment);
        return new ResponseEntity<>(new Response("Experimento Creado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/experimentos/eliminar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> deleteExperiment(@RequestBody Experiment experiment) throws SQLException {
        if (!validationsService.isRepetead("nombre", "experimento", experiment.getName())) {
            return new ResponseEntity<>(new Response("El experimento a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            experimentsDao.delete(experiment);
            return new ResponseEntity<>(new Response("Experimento Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/experimentos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyExperiment(@RequestBody Experiment experiment) throws SQLException {
        experimentsDao.update(experiment);
        return new ResponseEntity<>(new Response("Experimento Modificado", HttpStatus.OK.value()), HttpStatus.OK);
    }

}
