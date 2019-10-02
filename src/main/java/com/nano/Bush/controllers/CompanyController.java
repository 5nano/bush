package com.nano.Bush.controllers;

import com.nano.Bush.datasources.CompaniesDao;
import com.nano.Bush.model.Company;
import com.nano.Bush.model.Response;
import com.nano.Bush.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class CompanyController {

    @Autowired
    CompaniesDao companiesDao;
    @Autowired
    ValidationsService validationsService;

    @RequestMapping(value = "/companias/insertar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Response> insertCompany(@RequestBody Company company) throws SQLException {

        if (validationsService.isRepetead("nombre", "compania", company.getName())) {
            return new ResponseEntity<>(new Response("El nombre de la compania ya existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            companiesDao.insert(company);
            return new ResponseEntity<>(new Response("Compañía Creada", HttpStatus.OK.value()), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/companias", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Company>> showCompanies() throws SQLException {
        return new ResponseEntity<>(companiesDao.getCompanies(), HttpStatus.OK);
    }

    @RequestMapping(value = "/companias/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Response> deleteCompany(@RequestBody Company company) throws SQLException {

        if (!validationsService.isRepetead("nombre", "compania", company.getName())) {
            return new ResponseEntity<>(new Response("La compania a eliminar no existe", HttpStatus.CONFLICT.value()),
                    HttpStatus.CONFLICT);
        } else {
            companiesDao.delete(company.getName());
            return new ResponseEntity<>(new Response("Compania Eliminada", HttpStatus.OK.value()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/companias/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<Response> modifyCompany(@RequestBody Company company) throws SQLException {
        companiesDao.modify(company);
        return new ResponseEntity<>(new Response("Compania Modificada", HttpStatus.OK.value()), HttpStatus.OK);

    }
}

