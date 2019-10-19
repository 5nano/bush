package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.*;
import com.nano.Bush.services.AssayService;
import com.nano.Bush.services.ExperimentService;
import com.nano.Bush.services.TreatmentsService;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class AssayController {


    @Autowired
    AssayService assayService;
    @Autowired
    AssaysDao assaysDao;
    @Autowired
    ValidationsService validationsService;
    @Autowired
    TreatmentsService treatmentsService;

    @Autowired
    private ExperimentService experimentService;

    @RequestMapping(value = "/ensayos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<AssayInsertResponse> insertAssay(@RequestBody Assay assay) throws SQLException {
        return new ResponseEntity<>(assayService.insert(assay), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Assay>> showAssays(Optional<String> state) throws SQLException {
        List<Assay> assays;
        if(!state.isPresent()){
            assays = assayService.getAssays("ALL");
        }else{
            assays = assayService.getAssays(state.get());
        }
        return new ResponseEntity<>(assays, HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteAssay(@RequestBody Assay assay) throws SQLException {

        if (!validationsService.isRepetead("nombre", "ensayo", assay.getName())) {
            return new ResponseEntity<>(new Response("El ensayo a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            assayService.deleteTestFromExperiments(assay.getIdAssay().get());
            assaysDao.delete(assay.getIdAssay().get());
            return new ResponseEntity<>(new Response("Ensayo Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/ensayos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyAssay(@RequestBody Assay assay) throws SQLException {
        assaysDao.update(assay);
        return new ResponseEntity<>(new Response("Ensayo Modificado", HttpStatus.OK.value()), HttpStatus.OK);
    }


    @RequestMapping(value = "/ensayo/experimentos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Experiment>> showExperimentsFrom(@RequestParam Integer assayId) {
        return new ResponseEntity<>(experimentService.getExperimentsFromAssay(assayId), HttpStatus.OK);
    }


    @RequestMapping(value = "/ensayo/tratamientos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Treatment>> getTreatments(@RequestParam Integer idAssay) throws SQLException {
        return new ResponseEntity<>(treatmentsService.treatments(idAssay), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/archivar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> archiveAssay(@RequestParam Integer idAssay) throws SQLException {
        assayService.archiveAssay(idAssay);
        return new ResponseEntity<>(new Response("Ensayo Archivado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/activar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> activeAssay(@RequestParam Integer idAssay) throws SQLException {
        assayService.activeAssay(idAssay);
        return new ResponseEntity<>(new Response("Ensayo Activado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/terminar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> activeAssay(@RequestParam Integer idAssay, Integer stars, String comments) throws SQLException {
        assayService.finishAssay(idAssay, stars, comments);
        return new ResponseEntity<>(new Response("Ensayo Terminado", HttpStatus.OK.value()), HttpStatus.OK);
    }

}
