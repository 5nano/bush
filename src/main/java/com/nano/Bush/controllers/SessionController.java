package com.nano.Bush.controllers;

import com.nano.Bush.model.UserCredentials;
import com.nano.Bush.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.nano.Bush.utils.EncryptUtils.encode;


@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class SessionController {

  @Autowired
  UsersService usersService;

  @RequestMapping(value = "/session", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<?> getCurrentSession(HttpServletRequest request) {
    manageSession(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> validateUser(@RequestBody UserCredentials userCredentials, HttpServletRequest request, HttpServletResponse response) {

    if (usersService.isValidUser(userCredentials)) {
      addCookies(userCredentials, request, response);
      return new ResponseEntity<>(HttpStatus.OK.value(), HttpStatus.OK);
    } else
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED);
  }

  @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    invalidateSession(request);
    return new ResponseEntity<>(HttpStatus.OK);

  }

  private static void addCookies(UserCredentials userCredentials, HttpServletRequest request, HttpServletResponse response){
    manageSession(request);
    Cookie cookieUser = new Cookie("user", encode(userCredentials.username));
    cookieUser.setMaxAge(60 * 60 * 24 * 365);
    cookieUser.setPath("/");
    cookieUser.setHttpOnly(false);
    response.addCookie(cookieUser);
  }

  public static void manageSession(HttpServletRequest request) {
    invalidateSession(request);
    //generate a new session
    HttpSession newSession = request.getSession(true);
    //setting session to expiry in 1 anio
    newSession.setMaxInactiveInterval(60 * 60 * 24 * 365);
  }

  public static void invalidateSession(HttpServletRequest request){
    HttpSession session = request.getSession(false);
    if(session != null){
      session.invalidate();
    }
  }
}
