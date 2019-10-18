package com.nano.Bush.controllers.measures;

import com.nano.Bush.model.Experiment;
import com.nano.Bush.model.Response;
import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.TreatmentInsertResponse;
import com.nano.Bush.services.ExperimentService;
import com.nano.Bush.services.TreatmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class TreatmentController {

    @Autowired
    TreatmentsService treatmentsService;

    @Autowired
    private ExperimentService experimentService;

    @RequestMapping(value = "/tratamientos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<TreatmentInsertResponse> insertTreatment(@RequestBody Treatment treatment) throws SQLException {
        return new ResponseEntity<>(treatmentsService.insert(treatment), HttpStatus.OK);
    }

    @RequestMapping(value = "/tratamientos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> modifyTreatment(@RequestBody Treatment treatment) throws SQLException {
        treatmentsService.modify(treatment);
        return new ResponseEntity<>(new Response("Experimento Modificado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/tratamientos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> deleteTreatment(@RequestBody Integer idTreatment) throws SQLException {
        treatmentsService.delete(idTreatment);
        return new ResponseEntity<>(new Response("Experimento Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
    }


    @RequestMapping(value = "/tratamiento", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Treatment> getTreatment(@RequestParam Integer idTreatment) throws SQLException {
        return new ResponseEntity<>(treatmentsService.treatment(idTreatment), HttpStatus.OK);
    }

    @RequestMapping(value = "/tratamiento/experimentos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Experiment>> showExperimentsFrom(@RequestParam String treatmentId) {
        return new ResponseEntity<>(experimentService.getExperimentsFromTreatment(treatmentId), HttpStatus.OK);
    }


    @RequestMapping(value = "/tratamiento/QRs", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<TreatmentInsertResponse> getQRsTreatment(@RequestParam Integer idTreatment) throws SQLException {
        return new ResponseEntity<>(treatmentsService.getTreatmentQRs(idTreatment), HttpStatus.OK);
    }

}
