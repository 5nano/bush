package com.nano.Bush.controllers;

import com.nano.Bush.model.pressureIndicator.PressureIndicator;
import com.nano.Bush.services.PressureIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class PressureIndicatorController {

    @Autowired
    PressureIndicatorService pressureIndicatorService;

    @RequestMapping(value = "/tratamiento/presionAgroquimicos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<PressureIndicator>> getComparativeExperimentsData(@RequestParam Integer assayId) throws SQLException {
        return new ResponseEntity<>(pressureIndicatorService.getPressureIndicatorInfoFor(assayId), HttpStatus.OK);
    }


}