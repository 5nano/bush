package com.nano.Bush.controllers.measures;

import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Response;
import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.TreatmentInsertResponse;
import com.nano.Bush.services.TreatmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class TreatmentController {

    @Autowired
    TreatmentsService treatmentsService;

    @RequestMapping(value = "/tratamientos/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<TreatmentInsertResponse> insertTreatment(@RequestBody Treatment treatment) throws SQLException {
        return new ResponseEntity<>(treatmentsService.insert(treatment), HttpStatus.OK);
    }

}
