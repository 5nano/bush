package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.SankeyAssayDTO;
import com.nano.Bush.services.generalMetrics.SankeyAssayService;
import com.nano.Bush.utils.RequestHomeMadeInterceptor;
import io.vavr.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

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

    @Autowired
    private RequestHomeMadeInterceptor requestHomeMadeInterceptor;

    @RequestMapping(value = "/metricas/ensayos/sankey", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<SankeyAssayDTO> getMixturesAgrochemicals(@CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) throws SQLException {
        final Tuple3<Integer, Integer, String> tuple = requestHomeMadeInterceptor.extractUserCompany(user, user_encoded);
        return new ResponseEntity<>(sankeyAssayService.getSankeyAssays(tuple._1), HttpStatus.OK);
    }
}
