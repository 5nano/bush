package com.nano.Bush.controllers;

import com.nano.Bush.datasources.UsersDao;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.PUT})
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    ValidationsService validationsService;

    @Autowired
    UsersDao usersDao;

    @RequestMapping(value = "/usuarios/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> insertUser(@RequestBody User user) throws SQLException {

        if (validationsService.isRepetead("usuario", "usuario", user.getUsername())) {
            return new ResponseEntity<>(new Response("El nombre de usuario ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            usersService.insertUser(user);
            return new ResponseEntity<>(new Response("Usuario Creado", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> showCrops() throws SQLException {

        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/usuarios/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteCrop(@RequestBody User user) throws SQLException {

        if (!validationsService.isRepetead("usuario", "usuario", user.getUsername())) {
            return new ResponseEntity<>(new Response("El usuario a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            usersDao.delete(user.getUserId().get());
            return new ResponseEntity<>(new Response("Usuario Eliminado", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/usuarios/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyUser(@RequestBody User user) throws SQLException {

        if (!validationsService.isRepetead("usuario", "usuario", user.getUsername())) {
            return new ResponseEntity<>(new Response("El usuario a modificar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            usersService.updateUser(user);
            return new ResponseEntity<>(new Response("Usuario Modificado", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }


}
