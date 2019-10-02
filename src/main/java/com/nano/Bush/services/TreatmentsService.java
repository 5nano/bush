package com.nano.Bush.services;

import com.nano.Bush.model.Treatment;
import com.nano.Bush.model.TreatmentInsertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Zeitune oct. 2019
 **/
@Service
public class TreatmentsService {

    private static final Logger logger = LoggerFactory.getLogger(TreatmentsService.class);

    public TreatmentInsertResponse insert(Treatment treatment) throws SQLException {
        Map<String, String> experiments = new HashMap<>();
        for(Integer i = 0; i<treatment.getExperimentsLength(); i++){
            Integer realIndex = i+1;
            experiments.put(realIndex.toString(), treatment.getIdAssay().toString()+"-"+realIndex.toString());
        }
        return new TreatmentInsertResponse(experiments);
    }

}
