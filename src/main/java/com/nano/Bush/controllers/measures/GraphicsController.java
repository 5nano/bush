package com.nano.Bush.controllers.measures;

import com.nano.Bush.model.measuresGraphics.GraphicDto;
import com.nano.Bush.services.GraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class GraphicsController {

    @Autowired
    private GraphicsService graphicsService;

    @RequestMapping(value = "/graficoComparativo/experimentos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<GraphicDto>> getComparativeExperimentsData(@RequestParam Integer assayId) {

        return new ResponseEntity<>(graphicsService.getComparativeExperimentsData(assayId), HttpStatus.OK);
    }


}

