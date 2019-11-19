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
 * label: ["Ray Grass", "Mostaza", "Soja", "A", "B", "C", "Diurex", "Adengo", "Starane Xtra","Amicor", "Galant", "Unimark", "Hussar", "Solomon",
 * "Mospilan","Assail","4 Estrellas","3 Estrellas","5 Estrellas","1 estrella"],
 * source: [0,0,0,1,1,2,3,4,5,5,4,6,7,10,10,8,15,14],
 * target: [10,4,5,3,10,5,6,8,7,14,15,16,17,16,18,19,19,18],
 * value:  [3,3,2,1,2,1,1,2,2,2,1,1,3,2,1,1,2,1,1,1,1]
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
        final Tuple3<Integer, Integer, String> tuple = requestHomeMadeInterceptor.extractUserCompany(user_encoded, user);
        return new ResponseEntity<>(sankeyAssayService.getSankeyAssays(tuple._1), HttpStatus.OK);
    }
}
