package com.nano.Bush.controllers.measures;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.AssayInsertResponse;
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
    @Autowired
    AssaysDao assaysDao;
    @Autowired
    ValidationsService validationsService;

    @RequestMapping(value = "/ensayos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<AssayInsertResponse> insertAssay(@RequestBody Assay assay) throws SQLException {
        return new ResponseEntity<>(assayService.insert(assay), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Assay>> showAssays() throws SQLException {
        return new ResponseEntity<>(assayService.getAssays(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ensayos/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteAssay(@RequestBody Assay assay) throws SQLException {

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
        assaysDao.modify(assay);
        return new ResponseEntity<>(new Response("Ensayo Modificado", HttpStatus.OK.value()), HttpStatus.OK);
    }


}
