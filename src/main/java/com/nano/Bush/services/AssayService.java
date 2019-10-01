package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AssayService {

    private static final Logger logger = LoggerFactory.getLogger(AssayService.class);

    @Autowired
    AssaysDao assaysDao;

    public List<Assay> getAssays() throws SQLException {
        return assaysDao.getAssays();
    }

    public void insert(Assay assay) throws SQLException {
        assaysDao.insert(assay);
    }

}
