package com.nano.Bush.controllers.generalMetrics;

import com.nano.Bush.model.generalMetrics.HistogramDTO;
import com.nano.Bush.services.generalMetrics.HistogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class HistogramForPicturesByDayMetricController {
    @Autowired
    private HistogramService histogramService;

    //TODO: los ensayos tienen que ser la compania en la que estas parado
    @RequestMapping(value = "/metricas/histograma/pruebas", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<HistogramDTO> getMixturesAgrochemicals() {
        return new ResponseEntity<>(histogramService.getHistogramTest(), HttpStatus.OK);
    }

}
