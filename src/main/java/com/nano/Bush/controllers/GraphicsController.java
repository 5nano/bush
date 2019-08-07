package com.nano.Bush.controllers;

import com.nano.Bush.services.GraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphicsController {

    @Autowired
    private GraphicsService graphicsService;

    @GetMapping(value = "/bush/comparativeGraphic")
    @ResponseBody
    public String getComparativeInfoGraphic() {
        System.out.println("Me pegaron");
        return "graphicsService.getComparativeGraphicInfo()";
    }


}

