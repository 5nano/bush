package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.*;
import com.nano.Bush.services.AssayService;
import com.nano.Bush.services.ExperimentService;
import com.nano.Bush.services.TreatmentsService;
import com.nano.Bush.services.ValidationsService;
import com.nano.Bush.utils.RequestHomeMadeInterceptor;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class AssayController {

    private static final Logger logger = LoggerFactory.getLogger(AssayController.class);


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
    @Autowired
    private RequestHomeMadeInterceptor interceptor;

    @RequestMapping(value = "/ensayos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<AssayInsertResponse> insertAssay(@RequestBody Assay assay, @CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) throws SQLException {
        final Tuple3<Integer, Integer, String> tuple = interceptor.extractUserCompany(user_encoded, user);
        assay.setIdCompany(tuple._1);
        assay.setIdUserCreator(tuple._2);
        return new ResponseEntity<>(assayService.insert(assay), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<AssayResponse>> showAssays(Optional<String> state, HttpServletRequest request, @CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) {
        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("Received cookie {}:{}", cookie.getName(), cookie.getValue());
            }
        } else {
            logger.info("Empty cookies");
        }
        final Tuple3<Integer, Integer, String> tuple = interceptor.extractUserCompany(user_encoded, user);

        List<AssayResponse> assays = Option.ofOptional(state)
                .map(ste -> "ALL".equalsIgnoreCase(ste) ? assayService.getAllAssays(tuple._1, tuple._3) : assayService.getAssaysByState(tuple._1, ste,tuple._3))
                .getOrElse(assayService.getAllAssays(tuple._1, tuple._3));

        return new ResponseEntity<>(assays, HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Assay> getAssay(@RequestParam(value = "idAssay") Integer id, @CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) {

        //final Integer idCompany = interceptor.extractIdCompany(user_encoded,user);

        return new ResponseEntity<>(assayService.getAssay(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/ensayos/eliminar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> deleteAssay(@RequestParam Integer assayId) throws SQLException {

        if (!validationsService.isRepetead("idEnsayo", "ensayo", assayId)) {
            return new ResponseEntity<>(new Response("El ensayo a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            assayService.deleteTestFromExperiments(assayId);
            assaysDao.delete(assayId);
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
    ResponseEntity<List<TreatmentResponse>> getTreatments(@RequestParam Integer idAssay) {
        return new ResponseEntity<>(treatmentsService.getTreatmentsFrom(idAssay), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/archivar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> archiveAssay(@RequestParam Integer idAssay) throws SQLException {
        if (validationsService.isNotSameState(idAssay, "ARCHIVED")) {
            assayService.archiveAssay(idAssay);
        } else {
            throw new RuntimeException("Error, el ensayo ya fue archivado");
        }
        return new ResponseEntity<>(new Response("Ensayo Archivado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/activar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> activeAssay(@RequestParam Integer idAssay) throws SQLException {
        if (validationsService.isNotSameState(idAssay, "ACTIVE")) {
            assayService.activeAssay(idAssay);
        } else {
            throw new RuntimeException("Error, el ensayo ya fue activado");
        }
        return new ResponseEntity<>(new Response("Ensayo Activado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayo/terminar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> activeAssay(@RequestParam Integer idAssay, Integer stars, String comments) throws SQLException {
        if (validationsService.isNotSameState(idAssay, "FINISHED")) {
            assayService.finishAssay(idAssay, stars, comments);
        } else {
            throw new RuntimeException("Error, el ensayo ya fue finalizado");
        }
        return new ResponseEntity<>(new Response("Ensayo Terminado", HttpStatus.OK.value()), HttpStatus.OK);
    }

}
