package com.nano.Bush.services;

import com.nano.Bush.datasources.measures.TreatmentsDao;
import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.TreatmentInsertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@Service
public class TreatmentsService {

    private static final Logger logger = LoggerFactory.getLogger(TreatmentsService.class);

    @Autowired
    private TreatmentsDao treatmentsDao;

    public TreatmentInsertResponse insert(Treatment treatment) throws SQLException {
        Map<String, String> experiments = new HashMap<>();
        treatmentsDao.insert(treatment).stream().forEach(experimentId -> {
            experiments.put(experimentId.toString(), treatment.getIdAssay().toString()+"-"+experimentId.toString());
        });
        return new TreatmentInsertResponse(experiments);
    }

    public void modify(Treatment treatment) throws SQLException {
        treatmentsDao.update(treatment);
    }

    public void delete(Integer idTreatment) throws SQLException {
        treatmentsDao.delete(idTreatment);
    }

    public List<Treatment> treatments(Integer idAssay) throws SQLException {
        return  treatmentsDao.getTreatments(idAssay);
    }

    public Treatment treatment(Integer idTreatment) throws SQLException {
        return  treatmentsDao.getTreatment(idTreatment);
    }

}
