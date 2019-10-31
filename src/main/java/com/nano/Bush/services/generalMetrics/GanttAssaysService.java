package com.nano.Bush.services.generalMetrics;

import com.nano.Bush.datasources.measures.AssaysDao;
import com.nano.Bush.model.Assay;
import com.nano.Bush.model.generalMetrics.GanttMetricDTO;
import com.nano.Bush.model.generalMetrics.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GanttAssaysService {

    @Autowired
    AssaysDao assaysDao;

    public GanttMetricDTO getAssaysGantt() throws SQLException {

        List<Assay> assays = assaysDao.getAssays();
        List<JobDTO> jobDTOS = new ArrayList<>();

        assays.forEach(assay -> jobDTOS.add(new JobDTO(assay.getName(), assay.getCreated().get().toString(),
                "2019-02-22", assay.getState().get().toString())));//TODO: falta la fecha de finalización de cada ensayo

        return new GanttMetricDTO(jobDTOS);
    }

}
