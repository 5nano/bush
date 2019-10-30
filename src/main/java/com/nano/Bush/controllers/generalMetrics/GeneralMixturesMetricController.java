package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.GeneralMixturesDTO;
import com.nano.Bush.services.generalMetrics.GeneralMixturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class GeneralMixturesMetricController {


    @Autowired
    private GeneralMixturesService generalMixturesService;

    @RequestMapping(value = "/metricas/mezclasAgroquimicos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<GeneralMixturesDTO> getMixturesAgrochemicals() throws SQLException {
        return new ResponseEntity<>(generalMixturesService.getGeneralMixturesMetric(), HttpStatus.OK);
    }
}


/**
 * labels=[ "Eve", "Cain", "Seth", "Enos", "Noam", "Abel", "Awan", "Enoch", "Azura"],
 * parents=["",    "Eve",  "Eve",  "Seth", "Seth", "Eve",  "Eve",  "Awan",  "Eve" ],
 * values=[  65,    14,     12,     10,     2,      6,      6,      4,       4],
 * ]
 */