package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.AssayInsertResponse;
import com.nano.Bush.model.Treatment;
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
    @Autowired
    TreatmentsDao treatmentDao;

    public List<Assay> getAssays() throws SQLException {
        return assaysDao.getAssays();
    }

    public List<Treatment> getTreatmentsFrom(Integer assayId) {

        List<Treatment> treatments;

        try {
            treatments = treatmentDao.getTreatments(assayId);
        } catch (SQLException e) {
            logger.error("Error al obtener el nombre del experimento, exception: " + e);
            throw new RuntimeException("Error al obtener el nombre del experimento, exception: " + e);
        }
        return treatments;
    }


    public AssayInsertResponse insert(Assay assay) throws SQLException {
        Integer insertAndReturnIdAssay = assaysDao.insert(assay);
        return new AssayInsertResponse(insertAndReturnIdAssay);
    }

}
