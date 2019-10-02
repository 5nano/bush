package com.nano.Bush.controllers.measures;

import com.nano.Bush.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class TestsController {


    @Autowired
    private TestsService testsService;

    @RequestMapping(value = "/pruebas/imagenBase64", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<String> getBase64ImageFrom(@RequestParam String experimentId, @RequestParam String assayId) {
        return testsService.getBase64Image(experimentId, experimentId);
    }

}

