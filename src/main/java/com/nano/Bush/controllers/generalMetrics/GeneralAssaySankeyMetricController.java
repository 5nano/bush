package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import com.nano.Bush.services.generalMetrics.SankeyAssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * label: ["A1", "A2", "B1", "B2", "C1", "C2"],
 * source: [0,1,0,2,3,3],
 * target: [2,3,3,4,4,5],
 * value:  [8,4,2,8,4,2]
 */

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class GeneralAssaySankeyMetricController {

    @Autowired
    private SankeyAssayService sankeyAssayService;

    @RequestMapping(value = "/metricas/ensayos/sankey", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<SankeyAssayDTO> getMixturesAgrochemicals() throws SQLException {
        return new ResponseEntity<>(sankeyAssayService.getSankeyAssays(), HttpStatus.OK);
    }
}