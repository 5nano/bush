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

    public GanttMetricDTO getAssaysGantt(Integer idCompany) throws SQLException {

        List<JobDTO> jobDTOS = new ArrayList<>();

        Map<Integer, String> assayWithFinishedDates = assaysDao.getAssayTerminateDate();

        List<Assay> assays = assaysDao.getAllAssays(idCompany);

        assays.forEach(assay -> {
            if (assay.getState().get().toString().equals("FINISHED")) {
                jobDTOS.add(new JobDTO(assay.getName(), assay.getCreated().get().toString(),
                        assayWithFinishedDates.get(assay.getIdAssay().get()), assay.getState().get().toString()));
            } else {
                jobDTOS.add(new JobDTO(assay.getName(), assay.getCreated().get().toString(), assay.getEstimatedFinished().get().toString(),
                        assay.getState().get().toString()));
            }
        });

        return new GanttMetricDTO(jobDTOS);
    }


}
