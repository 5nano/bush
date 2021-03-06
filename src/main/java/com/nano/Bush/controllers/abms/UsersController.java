package com.nano.Bush.controllers.abms;

import com.nano.Bush.datasources.CompaniesDao;
import com.nano.Bush.datasources.UsersDao;
import com.nano.Bush.model.Company;
import com.nano.Bush.model.Response;
import com.nano.Bush.model.User;
import com.nano.Bush.services.UsersService;
import com.nano.Bush.services.ValidationsService;
import com.nano.Bush.utils.RequestHomeMadeInterceptor;
import io.vavr.Tuple3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private RequestHomeMadeInterceptor interceptor;

    @Autowired
    CompaniesDao companiesDao;

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

    @RequestMapping(value = "/usuarios/eliminar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> deleteCrop(@RequestParam Integer userId) throws SQLException {

        if (!validationsService.isRepetead("idUsuario", "usuario", userId)) {
            return new ResponseEntity<>(new Response("El usuario a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            usersDao.delete(userId);
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

    @RequestMapping(value = "/usuario", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserInfo> userInfo(@CookieValue(value = "user", required = false) Optional<String> user, @CookieValue(value = "user_encoded", required = false) Optional<String> user_encoded) throws SQLException {
        final Tuple3<Integer, Integer, String> tuple = interceptor.extractUserCompany(user_encoded, user);
        final String _userName = tuple._3;
        final Company _company =  companiesDao.getCompany(tuple._1).orElseThrow(() -> new RuntimeException("Company not found"));

        return new ResponseEntity<>(new UserInfo(_company.getName(),_userName), HttpStatus.OK);
    }

    static class UserInfo{
        String company;
        String userName;

        public UserInfo(String company, String userName) {
            this.company = company;
            this.userName = userName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }


}
