package com.nano.Bush.controllers;

import com.nano.Bush.model.AssayResponse;
import com.nano.Bush.model.Response;
import com.nano.Bush.model.Tag;
import com.nano.Bush.services.TagsService;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@Controller
@RequestMapping("")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
public class TagsController {


    @Autowired
    TagsService tagsService;

    @RequestMapping(value = "/tags/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Tag> insertTag(@RequestBody Tag tag) throws SQLException {

        return new ResponseEntity<>(tagsService.insert(tag), HttpStatus.OK);

    }

    @RequestMapping(value = "/tags/modificar", method = RequestMethod.PATCH, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> modifyTag(@RequestBody Tag tag) throws SQLException {
        tagsService.modify(tag);
        return new ResponseEntity<>(new Response("Tag modificado exitosamente", HttpStatus.OK.value()), HttpStatus.OK);

    }

    @RequestMapping(value = "/tags/ensayo/insertar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> insertTagToAssay(@RequestParam Integer idTag, Integer idAssay) throws SQLException {
        tagsService.insertIntoAssay(idTag, idAssay);
        return new ResponseEntity<>(new Response("Tag creado exitosamente de ensayo", HttpStatus.OK.value()), HttpStatus.OK);

    }

    @RequestMapping(value = "/tags/ensayo/eliminar", method = RequestMethod.DELETE, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Response> deleteTagToAssay(@RequestParam Integer idTag, Integer idAssay) throws SQLException {
        tagsService.deleteFromAssay(idTag, idAssay);
        return new ResponseEntity<>(new Response("Tag eliminado exitosamente de ensayo", HttpStatus.OK.value()), HttpStatus.OK);

    }


    @RequestMapping(value = "/tags", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Tag>> tags() throws SQLException {
        return new ResponseEntity<>(tagsService.getTags(), HttpStatus.OK);

    }

    @RequestMapping(value = "/tags/ensayos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<AssayResponse>> getAssaysWithTags(@RequestBody List<String> tags, Optional<String> state) {
        List<AssayResponse> assays = Option.ofOptional(state)
                .map(ste -> "ALL".equalsIgnoreCase(ste)? tagsService.getAllAssaysFrom(tags) : tagsService.getAssaysFromByState(tags,ste))
                .getOrElse(tagsService.getAllAssaysFrom(tags));
        return new ResponseEntity<>(assays, HttpStatus.OK);

    }

    @RequestMapping(value = "/ensayo/tags", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Tag>> getTagsOfAssay(@RequestParam Integer idAssay) {
        return new ResponseEntity<>(tagsService.getTagsFrom(idAssay), HttpStatus.OK);

    }
}
