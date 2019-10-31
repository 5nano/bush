package com.nano.Bush.controllers;

import com.nano.Bush.model.Response;
import com.nano.Bush.services.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping(value = "/mailSender", consumes = "application/json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> mailSender(@RequestBody String payload) throws Exception {
        emailSenderService.sendEmail(payload);
        return new ResponseEntity<>(new Response("Email enviado", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
