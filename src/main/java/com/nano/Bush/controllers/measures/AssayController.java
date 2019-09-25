package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.AssaysDao;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class AssayController {


    @Autowired
    AssayService assayService;

    @RequestMapping(value = "/ensayos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> insertAssay(@RequestBody Assay assay) throws SQLException {
        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("nombre", "ensayo", assay.getName())) {
            return new ResponseEntity<>(new Response("El nombre del ensayo ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            assayService.insert(assay);
            return new ResponseEntity<>(new Response("Ensayo Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Assay>> showAssays() throws SQLException {
        return new ResponseEntity<>(assayService.getAssays(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteAssay(@RequestBody Assay assay) throws SQLException {

        AssaysDao assaysDao = new AssaysDao();
        ValidationsService validationsService = new ValidationsService();

        if (!validationsService.isRepetead("nombre", "ensayo", assay.getName())) {
            return new ResponseEntity<>(new Response("El ensayo a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            assaysDao.delete(assay.getName());
            return new ResponseEntity<>(new Response("Ensayo Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/ensayos/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyAssay(@RequestBody Assay assay) throws SQLException {

        AssaysDao assaysDao = new AssaysDao();
        assaysDao.modify(assay);
        return new ResponseEntity<>(new Response("Ensayo Modificado", HttpStatus.OK.value()), HttpStatus.OK);
    }


}