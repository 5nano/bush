package com.nano.Bush.controllers;

import com.nano.Bush.conectors.PostgresConnector;
import com.nano.Bush.datasources.CropsDao;
import com.nano.Bush.model.Crop;
import com.nano.Bush.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class CropController {

    @RequestMapping(value = "/cultivo/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> saveCrop(@RequestBody Crop crop) throws SQLException {

        CropsDao cropsDao = new CropsDao(PostgresConnector.getInstance().getConnection());

        cropsDao.insertCrop(crop);

        return new ResponseEntity<>(new Response("Cultivo Creado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/cultivos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Crop>> showCrops() throws SQLException {
        CropsDao cropsDao = new CropsDao(PostgresConnector.getInstance().getConnection());

        return new ResponseEntity<>(cropsDao.getCrops(), HttpStatus.OK);
    }
}
