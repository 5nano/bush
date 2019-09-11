package com.nano.Bush.controllers;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.CropDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping("/bush")
public class CropController {

    @RequestMapping(value = "/cultivos/insertar", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> saveCrop(@RequestParam String cropName, @RequestParam String cropDescription) throws SQLException {
        CropDao cropDao = new CropDao(PostgresConnector.getInstance().getConnection());

        cropDao.insertCrop(cropName, cropDescription);

        return new ResponseEntity<>("Cultivo Creado", HttpStatus.OK);
    }
}
