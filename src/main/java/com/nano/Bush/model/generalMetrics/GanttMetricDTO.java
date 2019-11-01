package com.nano.Bush.model.generalMetrics;

import java.util.List;

public class GanttMetricDTO {
    private List<JobDTO> jobDTOS;

    public GanttMetricDTO(List<JobDTO> jobDTOS) {
        this.jobDTOS = jobDTOS;
    }

    public List<JobDTO> getJobDTOS() {
        return jobDTOS;
    }

    public void setJobDTOS(List<JobDTO> jobDTOS) {
        this.jobDTOS = jobDTOS;
    }
}
