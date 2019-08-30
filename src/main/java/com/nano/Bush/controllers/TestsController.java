package com.nano.Bush.controllers;

import com.nano.Bush.services.TestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bush")
public class TestsController {


    @Autowired
    private TestsService testsService;

    @RequestMapping(value = "/prueba/imagenBase64", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody
    String getComparativeInfoGraphic(@RequestParam String testId, @RequestParam String experimentId) {
        return testsService.getComparativeGraphicInfo(testId, experimentId);
    }
}

/*testsService.getComparativeGraphicInfo(testId, experimentId)*/

