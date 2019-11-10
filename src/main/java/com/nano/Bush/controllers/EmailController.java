package com.nano.Bush.controllers;

import com.nano.Bush.model.Response;
import com.nano.Bush.services.EmailSenderService;
import com.nano.Bush.utils.RequestHomeMadeInterceptor;
import io.vavr.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RequestHomeMadeInterceptor interceptor;

    @RequestMapping(value = "/mailSender", consumes = "application/json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> mailSender(@RequestBody PayloadEmail payload, @CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) throws Exception {
        final Tuple3<Integer, Integer, String> tuple = interceptor.extractUserCompany(user_encoded, user);
        emailSenderService.sendEmail(payload.getSubject(), payload.getBase64pdf(), payload.getHtml(), tuple._3, Optional.empty());
        return new ResponseEntity<>(new Response("Email enviado", HttpStatus.OK.value()), HttpStatus.OK);
    }


    @RequestMapping(value = "/mailSender/unregisteredUser", consumes = "application/json", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> mailSenderUnregisteredUser(@RequestParam String mail, @RequestBody PayloadEmail payload) throws Exception {
        emailSenderService.sendEmail(payload.getSubject(), payload.getBase64pdf(), payload.getHtml(), "bla", Optional.of(mail));
        return new ResponseEntity<>(new Response("Email enviado", HttpStatus.OK.value()), HttpStatus.OK);
    }

    static class PayloadEmail{
        String subject;
        String html;
        Optional<String> base64pdf = Optional.empty();

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public Optional<String> getBase64pdf() {
            return base64pdf;
        }

        public void setBase64pdf(Optional<String> base64pdf) {
            this.base64pdf = base64pdf;
        }
    }
}
