package com.nano.Bush.services;

import com.nano.Bush.datasources.TagsDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Matias Zeitune oct. 2019
 **/

@Service
public class TagsService {

    @Autowired
    TagsDao tagsDao;

    public Tag insert(Tag tag) throws SQLException {
        return tagsDao.insert(tag);
    }

    public void delete(String tagName) throws SQLException {
        tagsDao.delete(tagName);
    }

    public void modify(Tag tag) throws SQLException {
      tagsDao.modify(tag);
    }

    public void insertIntoAssay(Integer idTag, Integer idAssay) throws SQLException {
        tagsDao.insertIntoAssay(idTag,idAssay);
    }


    public void deleteFromAssay(Integer idTag, Integer idAssay) throws SQLException {
        tagsDao.deleteIntoAssay(idTag,idAssay);
    }

    public List<Tag> getTags() throws SQLException {
        return tagsDao.getTags();
    }

    public List<Tag> getTagsFrom(Integer idAssay) throws SQLException {
        return tagsDao.getTagsFrom(idAssay);
    }

    public List<Assay> getAssays(List<String> tags) throws SQLException {
        return tagsDao.getAssayFrom(tags);
    }
}
