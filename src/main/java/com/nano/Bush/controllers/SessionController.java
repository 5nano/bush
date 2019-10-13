package com.nano.Bush.controllers;

import com.nano.Bush.model.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class SessionController {
  @RequestMapping(value = "/session", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<?> validateUser(HttpServletRequest request, HttpServletResponse response) {
    manageSession(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public static void manageSession(HttpServletRequest request) {
    HttpSession oldSession = request.getSession(false);
    if (oldSession != null) {
      oldSession.invalidate();
    }
    //generate a new session
    HttpSession newSession = request.getSession(true);
    //setting session to expiry in 1 anio
    newSession.setMaxInactiveInterval(60 * 60 * 24 * 365);
  }
}
