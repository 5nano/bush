package com.nano.Bush.controllers;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.model.Agrochemical;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.ValidationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class AgrochemicalController {


    @RequestMapping(value = "/agroquimicos/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> insertAgrochemical(@RequestBody Agrochemical agrochemical) throws SQLException {

        AgrochemicalsDao agrochemicalsDao = new AgrochemicalsDao();
        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("nombre", "agroquimico", agrochemical.getName())) {
            return new ResponseEntity<>(new Response("El nombre del agroquimico ya existe", HttpStatus.UNAUTHORIZED.value()),
                    HttpStatus.UNAUTHORIZED);
        } else {
            agrochemicalsDao.insert(agrochemical);
            return new ResponseEntity<>(new Response("Agroquimico Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/agroquimicos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Agrochemical>> showAgrochemicals() throws SQLException {
        AgrochemicalsDao agrochemicalsDao = new AgrochemicalsDao();

        return new ResponseEntity<>(agrochemicalsDao.getAgrochemicals(), HttpStatus.OK);
    }

}

