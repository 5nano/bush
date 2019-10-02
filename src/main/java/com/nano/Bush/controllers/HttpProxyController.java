package com.nano.Bush.controllers;

import com.nano.Bush.model.bulmapsaur.BulmapsaurPayload;
import com.nano.Bush.model.stadistic.BoxDiagramaByExperiment;
import com.nano.Bush.services.bulmapsaur.BulmapsaurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class HttpProxyController {

  private static final Logger logger = LoggerFactory.getLogger(HttpProxyController.class);

  @Autowired  private BulmapsaurService bmsService;

  @RequestMapping(value = "/images", consumes = "application/json",method = RequestMethod.POST, produces = "application/json")
  public @ResponseBody ResponseEntity<String> handleRequest(@RequestBody BulmapsaurPayload payload) throws Exception {
    logger.info("Received image with idAssay {} and idExperiment {}", payload.idAssay, payload.idExperiment);
    return new ResponseEntity<>( bmsService.doPost(payload), HttpStatus.OK);
  }



}
