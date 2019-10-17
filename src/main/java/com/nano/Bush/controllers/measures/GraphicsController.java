package com.nano.Bush.controllers.measures;

import com.nano.Bush.model.measuresGraphics.GraphicDto;
import com.nano.Bush.model.measuresGraphics.GraphicLineTime;
import com.nano.Bush.services.GraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class GraphicsController {

    @Autowired
    private GraphicsService graphicsService;

   /* @RequestMapping(value = "/graficoComparativo/experimentos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<GraphicDto>> getComparativeExperimentsData(@RequestParam Integer assayId) {

        return new ResponseEntity<>(graphicsService.getComparativeExperimentsData(assayId), HttpStatus.OK);
    }*/

    @RequestMapping(value = "/graficoComparativo/tratamiento/experimentos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Map<Integer, List<GraphicLineTime>>> getComparativeTreatmentData(@RequestParam Integer treatmentId) throws SQLException {

        return new ResponseEntity<>(graphicsService.getComparativeTreatmentData(treatmentId), HttpStatus.OK);
    }


    @RequestMapping(value = "/graficoComparativo/tratamiento/promediado", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<GraphicLineTime>> getComparativeTreatmentAveragedData(@RequestParam Integer treatmentId) {

        return new ResponseEntity<>(graphicsService.getComparativeTreatmentAveragedData(treatmentId), HttpStatus.OK);
    }

    @RequestMapping(value = "/graficoComparativo/ensayo/tratamientos/promediado", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Map<Integer, List<GraphicLineTime>>> getComparativeTreatmentAveragedDataByAssay(@RequestParam Integer assayId) throws SQLException {

        return new ResponseEntity<>(graphicsService.getComparativeTreatmentAveragedDataByAssay(assayId), HttpStatus.OK);
    }

}

