package com.nano.Bush.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nano.Bush.datasources.TagsDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.AssayResponse;
import com.nano.Bush.model.AssayStatesEnum;
import com.nano.Bush.model.Tag;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Service
public class TagsService {

    private static final Logger logger = LoggerFactory.getLogger(TagsService.class);

    @Autowired
    TagsDao tagsDao;
    @Autowired
    AssayService assayService;

    public Tag insert(Tag tag) throws SQLException {
        return tagsDao.insert(tag);
    }

    public void modify(Tag tag) throws SQLException {
        tagsDao.update(tag);
    }

    public void insertIntoAssay(Integer idTag, Integer idAssay) throws SQLException {
        tagsDao.insertIntoAssay(idTag, idAssay);
    }


    public void deleteFromAssay(Integer idTag, Integer idAssay) throws SQLException {
        tagsDao.deleteIntoAssay(idTag, idAssay);
    }

    public List<Tag> getTags() throws SQLException {
        return tagsDao.getTags();
    }

    public List<Tag> getTagsFrom(Integer idAssay) {
        return Try.of(()->tagsDao.getTagsFrom(idAssay))
                .onFailure(error-> logger.error("Unexpected error getting tags for idAssay {}",idAssay,error))
                .getOrElse(Lists.newArrayList());

    }

    public Map<Integer, Set<Integer>> assayWithTags(){
        return Try.of(()-> tagsDao.assayWithTags())
                .onFailure(error-> logger.error("Unexpected error finding for tag-assays relationship ", error))
                .getOrElse(Maps.newHashMap());
    }

    public List<AssayResponse> getAllAssaysFrom(Integer idCompany,List<String> tags) {
        return Try.of(() -> tagsDao.getAllAssayFrom(idCompany,tags))
                .onFailure(e -> logger.error("Unexpected error", e))
                .map(assays -> assayService.enrichAssays(assays))
                .getOrElse(emptyList());
    }

    public List<AssayResponse> getAssaysFromByState(Integer idCompany,List<String> tags,String state) {
        AssayStatesEnum assayState = AssayStatesEnum.valueOf(state);
        return Try.of(() -> tagsDao.getAssaysFromByState(idCompany,tags, assayState))
                .onFailure(e -> logger.error("Unexpected error", e))
                .map(assays -> assayService.enrichAssays(assays))
                .getOrElse(emptyList());
    }
}
