package com.nano.Bush.controllers;

import com.nano.Bush.model.GraphicDto;
import com.nano.Bush.services.GraphicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/bush")
public class GraphicsController {

    @Autowired
    private GraphicsService graphicsService;


    @RequestMapping(value = "/comparativeGraphic", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<GraphicDto>> getComparativeInfoGraphic() {
        return new ResponseEntity<>(graphicsService.getComparativeGraphicInfo(), HttpStatus.OK);
    }


}

