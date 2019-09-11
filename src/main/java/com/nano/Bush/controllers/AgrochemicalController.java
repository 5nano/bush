package com.nano.Bush.controllers;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.AgrochemicalDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
@RequestMapping("/bush")
public class AgrochemicalController {

    @RequestMapping(value = "/agroquimico/insertar", method = RequestMethod.POST)
    public ResponseEntity<String> saveAgrochemical(@RequestParam String agrochemicalName, @RequestParam String agrochemicalDescription) throws SQLException {
        AgrochemicalDao agrochemicalDao = new AgrochemicalDao(PostgresConnector.getInstance().getConnection());

        agrochemicalDao.insertAgrochemical(agrochemicalName, agrochemicalDescription);

        return new ResponseEntity<>("Agroquimico Creado", HttpStatus.OK);
    }
}

