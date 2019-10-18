package com.nano.Bush.controllers;

import com.nano.Bush.datasources.AgrochemicalsDao;
import com.nano.Bush.model.Agrochemical;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class AgrochemicalController {

    @Autowired
    AgrochemicalsDao agrochemicalsDao;
    @Autowired
    ValidationsService validationsService;

    @RequestMapping(value = "/agroquimicos/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> insertAgrochemical(@RequestBody Agrochemical agrochemical) throws SQLException {

        if (validationsService.isRepetead("nombre", "agroquimico", agrochemical.getName())) {
            return new ResponseEntity<>(new Response("El nombre del agroquimico ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            agrochemicalsDao.insert(agrochemical);
            return new ResponseEntity<>(new Response("Agroquimico Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/agroquimicos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Agrochemical>> showAgrochemicals() throws SQLException {
        return new ResponseEntity<>(agrochemicalsDao.getAgrochemicals(), HttpStatus.OK);
    }

    @RequestMapping(value = "/agroquimicos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteAgrochemical(@RequestBody Agrochemical agrochemical) throws SQLException {

        if (!validationsService.isRepetead("nombre", "agroquimico", agrochemical.getName())) {
            return new ResponseEntity<>(new Response("El agroquimico a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            agrochemicalsDao.delete(agrochemical.getName());
            return new ResponseEntity<>(new Response("Agroquimico Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/agroquimicos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyAgrochemical(@RequestBody Agrochemical agrochemical) throws SQLException {
        agrochemicalsDao.update(agrochemical);
        return new ResponseEntity<>(new Response("Agroquimico Modificado", HttpStatus.OK.value()), HttpStatus.OK);

    }


}

