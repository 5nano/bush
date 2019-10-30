package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.GanttMetricDTO;
import com.nano.Bush.services.generalMetrics.GanttAssaysService;
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
public class GeneralGanttAssaysMetric {

    @Autowired
    private GanttAssaysService GanttAssaysService;

    @RequestMapping(value = "/metricas/ensayos/gantt", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<GanttMetricDTO> getMixturesAgrochemicals() throws SQLException {
        return new ResponseEntity<>(GanttAssaysService.getAssaysGantt(), HttpStatus.OK);
    }
}
