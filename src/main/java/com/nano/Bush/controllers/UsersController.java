package com.nano.Bush.controllers;

import com.nano.Bush.model.Response;
import com.nano.Bush.model.User;
import com.nano.Bush.services.UsersService;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/usuarios/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> insertUser(@RequestBody User user) throws SQLException {

        ValidationsService validationsService = new ValidationsService();

        if (validationsService.isRepetead("usuario", "usuario", user.getUsername())) {
            return new ResponseEntity<>(new Response("El nombre de usuario ya existe", HttpStatus.UNAUTHORIZED.value()),
                    HttpStatus.UNAUTHORIZED);
        } else {
            usersService.insertUser(user);
            return new ResponseEntity<>(new Response("Usuario Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> showCrops() throws SQLException {

        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

}
