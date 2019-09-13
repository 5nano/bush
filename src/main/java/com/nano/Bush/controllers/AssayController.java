package com.nano.Bush.controllers;

import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.AssayService;
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
    ResponseEntity<Response> insertUser(@RequestBody Assay assay) throws SQLException {
        assayService.insert(assay);
        return new ResponseEntity<>(new Response("Ensayo Creado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Assay>> showCrops() throws SQLException {
        return new ResponseEntity<>(assayService.getAssays(), HttpStatus.OK);
    }

}
