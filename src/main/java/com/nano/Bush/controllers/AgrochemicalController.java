package com.nano.Bush.controllers;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.AgrochemicalDao;
import com.nano.Bush.model.Agrochemical;
import com.nano.Bush.model.Response;
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
@RequestMapping("/bush")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class AgrochemicalController {

    @RequestMapping(value = "/agroquimico/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> saveAgrochemical(@RequestBody Agrochemical agrochemical) throws SQLException {
        AgrochemicalDao agrochemicalDao = new AgrochemicalDao(PostgresConnector.getInstance().getConnection());

        agrochemicalDao.insertAgrochemical(agrochemical);

        return new ResponseEntity<>(new Response("Agroquimico Creado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/agroquimicos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Agrochemical>> showAgrochemicals() throws SQLException {
        AgrochemicalDao agrochemicalDao = new AgrochemicalDao(PostgresConnector.getInstance().getConnection());

        return new ResponseEntity<>(agrochemicalDao.getAgrochemicals(), HttpStatus.OK);
    }

}

