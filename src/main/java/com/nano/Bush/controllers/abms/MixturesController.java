package com.nano.Bush.controllers.abms;

import com.nano.Bush.datasources.MixturesDao;
import com.nano.Bush.model.Mixture;
import com.nano.Bush.model.Response;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class MixturesController {

    @Autowired
    private MixturesDao mixturesDao;
    @Autowired
    private ValidationsService validationsService;

    @RequestMapping(value = "/mezclas/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> saveMixture(@RequestBody Mixture mixture) throws SQLException {
        if (validationsService.isRepetead("nombre", "mezcla", mixture.getName())) {
            return new ResponseEntity<>(new Response("El nombre de la mezcla ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            mixturesDao.insert(mixture);
            return new ResponseEntity<>(new Response("Mezcla Creada", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/mezclas", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Mixture>> showCrops() throws SQLException {
        return new ResponseEntity<>(mixturesDao.getMixtures(), HttpStatus.OK);
    }

    @RequestMapping(value = "/mezclas/eliminar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> deleteCrop(@RequestParam Integer mixtureId) throws SQLException {
        if (!validationsService.isRepetead("idMezcla", "mezcla", mixtureId)) {
            return new ResponseEntity<>(new Response("La mezcla a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            mixturesDao.delete(mixtureId);
            return new ResponseEntity<>(new Response("Mezcla Eliminada", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/mezclas/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyCrop(@RequestBody Mixture mixture) throws SQLException {
        mixturesDao.update(mixture);
        return new ResponseEntity<>(new Response("Mezcla Modificada", HttpStatus.OK.value()), HttpStatus.OK);

    }

}
