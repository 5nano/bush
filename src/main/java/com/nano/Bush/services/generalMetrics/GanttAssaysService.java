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
import java.util.Map;

@Service
public class GanttAssaysService {

    @Autowired
    AssaysDao assaysDao;

    public GanttMetricDTO getAssaysGantt() throws SQLException {

        List<Assay> assays = assaysDao.getAssays();
        List<JobDTO> jobDTOS = new ArrayList<>();
        Map<Integer, String> assayWithFinishedDates = assaysDao.getAssayTerminateDate();

        assays.forEach(assay -> jobDTOS.add(new JobDTO(assay.getName(), assay.getCreated().get().toString(),
                assayWithFinishedDates.get(assay.getIdAssay().get()), assay.getState().get().toString())));

        return new GanttMetricDTO(jobDTOS);
    }

}
