package com.nano.Bush.controllers;

import com.nano.Bush.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bush")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class TestsController {


    @Autowired
    private TestsService testsService;

    @RequestMapping(value = "/prueba/imagenBase64", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<String> getComparativeInfoGraphic(@RequestParam String testId, @RequestParam String experimentId) {
        return testsService.getBase64Image(testId, experimentId);
    }
}

