package com.nano.Bush.controllers;

import com.nano.Bush.datasources.CropsDao;
import com.nano.Bush.model.Crop;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.ValidationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class CropController {

    @RequestMapping(value = "/cultivos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> saveCrop(@RequestBody Crop crop) throws SQLException {

        CropsDao cropsDao = new CropsDao();
        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("nombre", "cultivo", crop.getName())) {
            return new ResponseEntity<>(new Response("El nombre del Cultivo ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            cropsDao.insert(crop);
            return new ResponseEntity<>(new Response("Cultivo Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/cultivos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Crop>> showCrops() throws SQLException {
        CropsDao cropsDao = new CropsDao();

        return new ResponseEntity<>(cropsDao.getCrops(), HttpStatus.OK);
    }

    @RequestMapping(value = "/cultivos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteCrop(@RequestBody Crop crop) throws SQLException {

        CropsDao cropsDao = new CropsDao();
        ValidationsService validationsService = new ValidationsService();

        if (!validationsService.isRepetead("nombre", "cultivo", crop.getName())) {
            return new ResponseEntity<>(new Response("El cultivo a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            cropsDao.delete(crop.getName());
            return new ResponseEntity<>(new Response("Cultivo Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/cultivos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyCrop(@RequestBody Crop crop) throws SQLException {

        CropsDao cropsDao = new CropsDao();
        cropsDao.modify(crop);
        return new ResponseEntity<>(new Response("Cultivo Modificado", HttpStatus.OK.value()), HttpStatus.OK);

    }
}
