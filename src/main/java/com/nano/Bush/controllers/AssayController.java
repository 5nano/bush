package com.nano.Bush.controllers;

import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.AssayService;
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
public class AssayController {


    @Autowired
    AssayService assayService;

    @RequestMapping(value = "/ensayos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> insertAssay(@RequestBody Assay assay) throws SQLException {
        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("nombre", "ensayo", assay.getName())) {
            return new ResponseEntity<>(new Response("El nombre del ensayo ya existe", HttpStatus.UNAUTHORIZED.value()),
                    HttpStatus.UNAUTHORIZED);
            //TODO: averiguar que status code hay que poner en estos casos.
        } else {
            assayService.insert(assay);
            return new ResponseEntity<>(new Response("Ensayo Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Assay>> showAssays() throws SQLException {
        return new ResponseEntity<>(assayService.getAssays(), HttpStatus.OK);
    }

}
