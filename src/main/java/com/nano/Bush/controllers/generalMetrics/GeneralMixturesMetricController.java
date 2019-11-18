package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.GeneralMixturesDTO;
import com.nano.Bush.services.generalMetrics.GeneralMixturesService;
import com.nano.Bush.utils.RequestHomeMadeInterceptor;
import io.vavr.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class GeneralMixturesMetricController {

    @Autowired
    private GeneralMixturesService generalMixturesService;

    @Autowired
    private RequestHomeMadeInterceptor requestHomeMadeInterceptor;

    @RequestMapping(value = "/metricas/mezclasAgroquimicos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<GeneralMixturesDTO> getMixturesAgrochemicals(@CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) throws SQLException {
        final Tuple3<Integer, Integer, String> tuple = requestHomeMadeInterceptor.extractUserCompany(user_encoded, user);
        return new ResponseEntity<>(generalMixturesService.getGeneralMixturesMetric(tuple._1), HttpStatus.OK);
    }
}
